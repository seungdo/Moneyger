package com.example.eos.searchtoolbar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsy01 on 2015-11-08.
 */
public class ListAdapter extends BaseAdapter{
    List<DataInfo> mList;//원본 데이터 클래스의 리스트
    List<DataInfo> fList;//필터링된 데이터 클래스의 리스트
    Context mContext;//리스트뷰의 부모 컨텍스트
    ListFilter filter;//필터 객체

    public ListAdapter(List<DataInfo> list, Context context){
        mList = list;
        fList = list;
        mContext = context;
        filter = new ListFilter();
    }
    public void clear(){//어댑터의 데이터 리스트를 지워버리는 메서드.
        mList.clear();
        fList = mList;
    }
    public void add(DataInfo dInst){//어댑터의 데이터 리스트에 데이터 객체 하나를 삽입하는 메서드.
        mList.add(dInst);
        fList = mList;
        notifyDataSetChanged();
    }
    public void addAll(List<DataInfo> dList){//어댑터의 데이터 리스트에 데이터 리스트를 통째로 삽입하는 메서드.
        mList.addAll(dList);
        fList = mList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public DataInfo getItem(int i) {
        return fList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public ListFilter getFilter(){//필터 객체 반환.
        return filter;
    }
    public List<DataInfo> getCheckedList(){//체크되어있는 리스트뷰 아이템의 데이터 클래스 리스트를 반환하는 메서드.
        List<DataInfo> cList = new ArrayList<>();
        for(int i=0;i<mList.size();i++){
            DataInfo mInst = mList.get(i);
            if(mInst.isCheck_state())
                cList.add(mInst);
        }
        return cList;
    }
    public void removeChecked(List<DataInfo> checked){//외부에서 받아온 리스트를 원본 리스트에서 제거하는 메서드. 외부에서 체크된 리스트를 받아오게 됨.
        for(int i=0;i<checked.size();i++){
            mList.remove(checked.get(i));
        }
        fList = mList;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final DataInfo mInst = fList.get(i);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_main, viewGroup, false);
        }
        TextView nameText = (TextView) view.findViewById(R.id.tv_name);//빚쟁이 이름 객체 동기화
        nameText.setText(mInst.getName());//빚쟁이 이름값 세팅
        TextView pnumText = (TextView) view.findViewById(R.id.tv_pnum);
        pnumText.setText(mInst.getPnum());//빚쟁이 폰번 세팅
        TextView deptText = (TextView) view.findViewById(R.id.tv_dept);
        deptText.setText(mInst.getDept()+"");//빚 금액 세팅

        CheckBox chk = (CheckBox) view.findViewById(R.id.chk_item);
        chk.setChecked(mInst.isCheck_state());//현재 데이터 클래스 객체의 check_state값으로 체크박스 상태를 세팅.
        chk.setOnClickListener(new View.OnClickListener() {//체크박스의 클릭리스너.
            @Override
            public void onClick(View view) {
                CheckBox v = (CheckBox)view;
                if(v.isChecked())//체크박스가 체크된 상태일 때.
                    mInst.setCheck_state(true);//데이터 클래스 객체의 check_state값을 true로 초기화.
                else//언체크드일 때
                    mInst.setCheck_state(false);//false로 초기화.
            }
        });
        return view;
    }

    class ListFilter extends Filter{//검색창에 적힌 텍스트 값을 받아와 일치하는 결과만 띄우도록 하는 필터 클래스.

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {//필터를 수행하는 메서드. 입력받은 텍스트값으로 원본 데이터 리스트에서 값 비교하여 일치하는 것들만 nList에 삽입.
            String filterString = charSequence.toString();

            FilterResults results = new FilterResults();

            final List<DataInfo> list = mList;

            int count = list.size();
            final ArrayList<DataInfo> nlist = new ArrayList<>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getName();
                if (filterableString.contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;//필터결과 반환.
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {//필터 결과를 리스트뷰에 적용하는 메서드.
            fList = (ArrayList<DataInfo>) filterResults.values;//fList를 필터링한 결과 리스트로 참조값을 바꾸어주고
            notifyDataSetChanged();//데이터셋 변화를 감지하여 리스트뷰를 갱신한다.
        }
    }
}
