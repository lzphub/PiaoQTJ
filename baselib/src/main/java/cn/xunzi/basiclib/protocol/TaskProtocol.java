package cn.xunzi.basiclib.protocol;

public interface TaskProtocol extends BaseRouteProtocol {
    String PART = "/task/";

    String TASKDATING=PART+"taskdating";

    String SUBMITTASK=PART+"submittask";

    String TASKRECORD=PART+"taskrecord";

    String TASKLIST=PART+"tasklist";

    String NEWBLETASK=PART+"newbletask";

    String TASKDETA=PART+"taskdeta";

    String SUBTASKVOUCHER=PART+"subtaskvoucher";
}
