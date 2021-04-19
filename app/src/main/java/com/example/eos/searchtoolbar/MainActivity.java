package com.example.eos.searchtoolbar;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<DataInfo> dlist = new ArrayList<>();//리스트뷰의 어댑터에 들어갈 값 세팅용 데이터 클래스의 리스트
    ListAdapter adapter;//어댑터
    static boolean allchecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateList(6);

        Toolbar tbr = (Toolbar)findViewById(R.id.tbr_main);//툴바 객체. 롤리팝버전부터 추가된 액션바 대체 뷰이다.(이 앱의 상단 노란색 바)
        final ListView lv = (ListView)findViewById(R.id.lv_main);//리스트뷰 객체.
        EditText searchEdit = (EditText)findViewById(R.id.edt_search);//에딧텍스트 객체. 여기에 적힌 내용을 실시간으로 필터에 적용하여 리스트뷰를 갱신한다.
        adapter = new ListAdapter(dlist, this);
        lv.setAdapter(adapter);

        tbr.inflateMenu(R.menu.menu_main);//툴바의 메뉴를 구현.
        tbr.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_18dp));//툴바의 오버플로우 메뉴 아이콘 설정.
        tbr.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {//툴바의 메뉴 클릭 리스너.
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.menu_delete){//선택한 목록의 사람의 빚을 탕감할 때.
                    List<DataInfo> cList = adapter.getCheckedList();
                    adapter.removeChecked(cList);//체크된 리스트를 인풋으로 하여 해당 리스트를 원본 리스트에서 제거하는 메서드 실행.
                    return true;
                }
                else if(item.getItemId()==R.id.menu_urge){//선택한 목록의 사람에게 독촉메시지를 보낼 때. 인텐트같은 방법을 통해 체크된 리스트의 폰번으로 메시지를 보내는 코드를 작성.
                    List<DataInfo> cList = adapter.getCheckedList();//체크된 리스트 가져옴.
                    for(int i=0;i<cList.size();i++)
                        Toast.makeText(getApplicationContext(), "Checked Item : "+cList.get(i).getName(), Toast.LENGTH_SHORT).show();//체크된 것을 정상적으로 받아오는지 테스트하기 위해 띄워준 메시지.
                    return true;
                }
                else if(item.getItemId()==R.id.menu_selectall){//전체선택일 때
                    if(allchecked==false){//전체선택 부울값이 false일 때.
                        for(int i=0;i<dlist.size();i++){
                            DataInfo dInst = dlist.get(i);
                            dInst.setCheck_state(true);//전부 true로.
                        }
                        adapter.notifyDataSetChanged();
                        allchecked = true;
                    }
                    else{//전체선택 부울값이 true일 때
                        for(int i=0;i<dlist.size();i++){
                            DataInfo dInst = dlist.get(i);
                            dInst.setCheck_state(false);//전부 false로.
                        }
                        adapter.notifyDataSetChanged();
                        allchecked = false;
                    }
                    return true;
                }
                return false;
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {//에딧텍스트의 텍스트 변화를 감지하는 리스너.
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //얘는 무시해도됨.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {//텍스트가 변화할 때 실행되는 메서드. 에딧텍스트에 적힌 내용을 실시간으로 처리하고 싶을 때
                                                                                        //이 메서드에서 구현해주면 된다.
                //얘가 중요.
                MainActivity.this.adapter.getFilter().publishResults(charSequence, MainActivity.this.adapter.getFilter().performFiltering(charSequence));//어댑터의 필터를 가져와 필터링.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //얘는 무시해도됨.
            }
        });
    }
    public void onCreateList(int i){//DB구현 전이라 임의로 데이터 클래스 객체의 리스트를 만들어 리스트뷰에 띄워주기 위해 만든 메서드.DB 구현후엔 쿼리한 값들을 DataInfo에 세팅하고 리스트에 넣으면됨.
        int j = 0;
        while(j<i){
            DataInfo dInst = new DataInfo();
            dInst.setName("ㅇㅅㅇ"+j);
            dInst.setPnum("0101234567" + j);
            dInst.setCheck_state(false);
            dInst.setDept(10000);
            dlist.add(dInst);
            j++;
        }
    }
}
