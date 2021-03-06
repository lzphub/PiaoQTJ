package cn.xunzi.basiclib.util;

import cn.xunzi.basiclib.R;

public class StateUtil {

    public static String WorkListState(int stateId){
        String state="";
        switch (stateId){
            case 3:
                state="工单已认领，审核中";
                break;
            case 4:
                state="工单已认领，审核通过";
                break;
            case 5:
                state="工单已认领，审核拒绝";
                break;
            case 6:
                state="工单已完成，审核中";
                break;
            case 7:
                state="工单已完成，审核通过";
                break;
            case 8:
                state="工单已完成，审核拒绝";
                break;
        }
        return state;
    }

    public  static String intentionStatus(int statusId){
        String status="";
        switch (statusId){
            case 1:
                status="SUBMITTED";
                break;
            case 2:
                status="RECEIVED";
                break;
            case 3:
                status="IN PROGRESS";
                break;
            case 4:
                status="UNDELIVERED";
                break;
            case 5:
                status="FINISH";
                break;
        }
        return status;
    }

    public static String ideaState(int stateId){
        String state="";
        switch (stateId){
            case 1:
                state="创意待处理";
                break;
            case 2:
                state="创意申请拒绝";
                break;
            case 3:
                state="创意申请中";
                break;
            case 4:
                state="创意申请完成";
                break;
            case 5:
                state="产品供应验证";
                break;
            case 6:
                state="产品推广中";
                break;
        }
        return state;
    }


    public static String requestState(int stateId){
        String state = "";
        switch (stateId){
            case 1:
                state="SUBMITTED";
                break;
            case 2:
                state="RECEIVED";
                break;
            case 3:
                state="IN PROGRESS";
                break;
            case 4:
                state="UNDELIVERED";
                break;
            case 5:
                state="FINISH";
                break;
        }
        return state;
    }
}
