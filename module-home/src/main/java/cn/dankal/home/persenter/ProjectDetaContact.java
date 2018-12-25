package cn.dankal.home.persenter;

import cn.dankal.basiclib.base.BasePresenter;
import cn.dankal.basiclib.base.BaseView;
import cn.dankal.basiclib.bean.ProjectDataBean;

public interface ProjectDetaContact {
    interface pcview extends BaseView {
        void getProjectDataSuccess(ProjectDataBean projectDataBean);
    }

    interface productPresenter extends BasePresenter<pcview> {
        void getProjectData(String projectId);

    }
}
