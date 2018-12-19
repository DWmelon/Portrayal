package com.portrayal.open.bean;

import java.util.List;

public class ConfigFileBean {


    /**
     * view : {"type":"view","list":[{"id":1,"name":"com.gaokaozhiyuan.module.home_v7.PlanningFragment","key":"home_view","description":"APP首页","extra_keys":["activity_id","activity_content"]},{"id":2,"name":"com.gaokaozhiyuan.module.me.MeFragment","key":"me_view","description":"我的页面"},{"id":3,"name":"com.gaokaozhiyuan.module.home_v6.accecesch.base.AccessCWBSchActivity","key":"select_sch_view","description":"我能上的学校一级界面"},{"id":4,"name":"com.gaokaozhiyuan.module.home_v6.accecesch.AccessSchEnterActivity","key":"select_sch_list_view","description":"我能上的学校列表界面"},{"id":6,"name":"com.gaokaozhiyuan.module.search.v7.SearchAssociationFragment","key":"search_relevance_view","description":"搜索关联结果界面"},{"id":7,"name":"com.gaokaozhiyuan.module.search.v7.SearchContentFragment","key":"search_result_view","description":"搜索结果界面"},{"id":9,"name":"com.gaokaozhiyuan.module.account.LoginPwFragment","key":"login_psw_view","description":"登录界面-密码登录"},{"id":8,"name":"com.gaokaozhiyuan.module.account.LoginSmsFragment","key":"login_sms_view","description":"登录界面-短信登录"},{"id":10,"name":"com.gaokaozhiyuan.module.account.SignupFragment","key":"signin_view","description":"注册界面"},{"id":11,"name":"com.gaokaozhiyuan.module.account.SignupFragment","key":"requst_login_view","description":"请求登录对话框"},{"id":12,"name":"com.gaokaozhiyuan.module.pay.VipCardShopActivity","key":"package_view","description":"套餐选择页"},{"id":13,"name":"com.gaokaozhiyuan.module.pay.VipCardBuyActivity","key":"pay_view","description":"支付确认页"},{"id":14,"name":"com.gaokaozhiyuan.module.pay.VipCardSuccessActivity","key":"pay_result_view","description":"支付成功页"},{"id":15,"name":"activity_dialog","key":"activity_dialog","description":"首页活动弹窗","extra_keys":["activity_id","activity_content"]}]}
     * click : {"type":"click","list":[{"res_id":"rl_home_v7_search_mess","key":"view_msg","description":"APP查询消息"},{"res_id":"iv_home_v7_score_editor","key":"update_score","description":"修改成绩"},{"res_id":"ll_home_v7_score_rank_content","key":"update_score","description":"修改成绩"},{"res_id":"l_home_v7_volunteer_step","key":"ten_steps","description":"十步法"},{"res_id":"ll_home_v7_xtb_sch","key":"select_sch","description":"选学校"},{"res_id":"rl_home_v7_collect_sch","key":"mark_sch","description":"有意报考学校"},{"res_id":"ll_home_v7_xtb_major","key":"select_major","description":"选专业"},{"res_id":"rl_home_v7_collect_major","key":"mark_major","description":"有意报考专业"},{"res_id":"ll_home_v7_xtb_zy","key":"select_zy","description":"报志愿"},{"res_id":"rl_home_v7_collect_zy","key":"my_zyb","description":"我的志愿表"},{"res_id":"home_func_all_sch","key":"all_sch","description":"院校大全"},{"res_id":"home_func_all_major","key":"all_major","description":"专业大全"},{"res_id":"home_func_all_profession","key":"all_profession","description":"职业大全"},{"res_id":"home_func_360_evaluate","key":"360_evaluate","description":"360环评"},{"res_id":"home_func_measure_probability","key":"measure_probability","description":"测录取概率"},{"res_id":"home_func_zy_by_inc","key":"zy_by_inc","description":"名企推荐"},{"res_id":"home_func_manual_fill_zy","key":"manual_fill_zy","description":"手动填志愿"},{"res_id":"home_func_policy_elucidation","key":"policy_elucidation","description":"填报政策解读"},{"res_id":"home_func_one_minute","key":"one_minute","description":"一分钟教你填"},{"res_id":"home_func_report_evaluate","key":"report_evaluate","description":"填报能力测试"},{"res_id":"home_func_select_course","key":"select_course","description":"新高考选科"},{"res_id":"home_func_batch_line","key":"batch_line","description":"批次线查询"},{"res_id":"ll_home_v7_search_content","key":"search","description":"搜索"},{"res_id":"","key":"activity_dialog","description":"首页活动弹窗","extra_keys":["dialog_id","dialog_content"]},{"res_id":"","key":"banner","description":"banner","extra_keys":["banner_id","banner_position"]},{"id":10023,"key":"push","description":"推送点击","extra_keys":["push_id","push_type"]},{"id":10024,"res_id":"","key":"vip_intent","description":"开通VIP","extra_keys":["promo_price"]},{"id":10025,"res_id":"","key":"vip_pay","description":"立即支付","extra_keys":["promo_price"]}]}
     * other : [{"type":"receive","list":[{"id":20000,"key":"push","description":"收到推送","extra_keys":["push_id","push_type"]}]},{"type":"misc","list":[{"id":30000,"key":"install_app","description":"APP安装应用列表","extra_keys":["install_app"]}]}]
     */

    private ViewModel view;
    private ClickModel click;
    private List<OtherModel> other;

    public ViewModel getView() {
        return view;
    }

    public void setView(ViewModel view) {
        this.view = view;
    }

    public ClickModel getClick() {
        return click;
    }

    public void setClick(ClickModel click) {
        this.click = click;
    }

    public List<OtherModel> getOther() {
        return other;
    }

    public void setOther(List<OtherModel> other) {
        this.other = other;
    }

    public static class ViewModel {
        /**
         * type : view
         * list : [{"id":1,"name":"com.gaokaozhiyuan.module.home_v7.PlanningFragment","key":"home_view","description":"APP首页"},{"id":2,"name":"com.gaokaozhiyuan.module.me.MeFragment","key":"me_view","description":"我的页面"},{"id":3,"name":"com.gaokaozhiyuan.module.home_v6.accecesch.base.AccessCWBSchActivity","key":"select_sch_view","description":"我能上的学校一级界面"},{"id":4,"name":"com.gaokaozhiyuan.module.home_v6.accecesch.AccessSchEnterActivity","key":"select_sch_list_view","description":"我能上的学校列表界面"},{"id":6,"name":"com.gaokaozhiyuan.module.search.v7.SearchAssociationFragment","key":"search_relevance_view","description":"搜索关联结果界面"},{"id":7,"name":"com.gaokaozhiyuan.module.search.v7.SearchContentFragment","key":"search_result_view","description":"搜索结果界面"},{"id":9,"name":"com.gaokaozhiyuan.module.account.LoginPwFragment","key":"login_psw_view","description":"登录界面-密码登录"},{"id":8,"name":"com.gaokaozhiyuan.module.account.LoginSmsFragment","key":"login_sms_view","description":"登录界面-短信登录"},{"id":10,"name":"com.gaokaozhiyuan.module.account.SignupFragment","key":"signin_view","description":"注册界面"},{"id":11,"name":"com.gaokaozhiyuan.module.account.SignupFragment","key":"requst_login_view","description":"请求登录对话框"},{"id":12,"name":"com.gaokaozhiyuan.module.pay.VipCardShopActivity","key":"package_view","description":"套餐选择页"},{"id":13,"name":"com.gaokaozhiyuan.module.pay.VipCardBuyActivity","key":"pay_view","description":"支付确认页"},{"id":14,"name":"com.gaokaozhiyuan.module.pay.VipCardSuccessActivity","key":"pay_result_view","description":"支付成功页"},{"id":15,"name":"activity_dialog","key":"activity_dialog","description":"首页活动弹窗","extra_keys":["activity_id","activity_content"]}]
         */

        private String type;
        private List<ListModel> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListModel> getList() {
            return list;
        }

        public void setList(List<ListModel> list) {
            this.list = list;
        }

        public static class ListModel {
            /**
             * id : 1
             * name : com.gaokaozhiyuan.module.home_v7.PlanningFragment
             * key : home_view
             * description : APP首页
             * extra_keys : ["activity_id","activity_content"]
             */

            private long id;
            private String name;
            private String key;
            private String description;
            private List<String> extra_keys;
            private String category;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getExtra_keys() {
                return extra_keys;
            }

            public void setExtra_keys(List<String> extra_keys) {
                this.extra_keys = extra_keys;
            }
        }
    }

    public static class ClickModel {
        /**
         * type : click
         * list : [{"res_id":"rl_home_v7_search_mess","key":"view_msg","description":"APP查询消息"},{"res_id":"iv_home_v7_score_editor","key":"update_score","description":"修改成绩"},{"res_id":"ll_home_v7_score_rank_content","key":"update_score","description":"修改成绩"},{"res_id":"l_home_v7_volunteer_step","key":"ten_steps","description":"十步法"},{"res_id":"ll_home_v7_xtb_sch","key":"select_sch","description":"选学校"},{"res_id":"rl_home_v7_collect_sch","key":"mark_sch","description":"有意报考学校"},{"res_id":"ll_home_v7_xtb_major","key":"select_major","description":"选专业"},{"res_id":"rl_home_v7_collect_major","key":"mark_major","description":"有意报考专业"},{"res_id":"ll_home_v7_xtb_zy","key":"select_zy","description":"报志愿"},{"res_id":"rl_home_v7_collect_zy","key":"my_zyb","description":"我的志愿表"},{"res_id":"home_func_all_sch","key":"all_sch","description":"院校大全"},{"res_id":"home_func_all_major","key":"all_major","description":"专业大全"},{"res_id":"home_func_all_profession","key":"all_profession","description":"职业大全"},{"res_id":"home_func_360_evaluate","key":"360_evaluate","description":"360环评"},{"res_id":"home_func_measure_probability","key":"measure_probability","description":"测录取概率"},{"res_id":"home_func_zy_by_inc","key":"zy_by_inc","description":"名企推荐"},{"res_id":"home_func_manual_fill_zy","key":"manual_fill_zy","description":"手动填志愿"},{"res_id":"home_func_policy_elucidation","key":"policy_elucidation","description":"填报政策解读"},{"res_id":"home_func_one_minute","key":"one_minute","description":"一分钟教你填"},{"res_id":"home_func_report_evaluate","key":"report_evaluate","description":"填报能力测试"},{"res_id":"home_func_select_course","key":"select_course","description":"新高考选科"},{"res_id":"home_func_batch_line","key":"batch_line","description":"批次线查询"},{"res_id":"ll_home_v7_search_content","key":"search","description":"搜索"},{"res_id":"","key":"activity_dialog","description":"首页活动弹窗","extra_keys":["dialog_id","dialog_content"]},{"res_id":"","key":"banner","description":"banner","extra_keys":["banner_id","banner_position"]},{"id":10023,"key":"push","description":"推送点击","extra_keys":["push_id","push_type"]},{"id":10024,"res_id":"","key":"vip_intent","description":"开通VIP","extra_keys":["promo_price"]},{"id":10025,"res_id":"","key":"vip_pay","description":"立即支付","extra_keys":["promo_price"]}]
         */

        private String type;
        private List<ListModelX> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListModelX> getList() {
            return list;
        }

        public void setList(List<ListModelX> list) {
            this.list = list;
        }

        public static class ListModelX {
            /**
             * page_id : 1
             * name : rl_home_v7_search_mess
             * key : view_msg
             * description : APP查询消息
             * extra_keys : ["dialog_id","dialog_content"]
             * id : 10023
             */

            private long page_id;
            private String name;
            private String key;
            private String description;
            private long id;
            private List<String> extra_keys;
            private String category;

            public long getPage_id() {
                return page_id;
            }

            public void setPage_id(long page_id) {
                this.page_id = page_id;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public List<String> getExtra_keys() {
                return extra_keys;
            }

            public void setExtra_keys(List<String> extra_keys) {
                this.extra_keys = extra_keys;
            }
        }
    }

    public static class OtherModel {
        /**
         * type : receive
         * list : [{"id":20000,"key":"push","description":"收到推送","extra_keys":["push_id","push_type"]}]
         */

        private String type;
        private List<ListModelXX> list;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ListModelXX> getList() {
            return list;
        }

        public void setList(List<ListModelXX> list) {
            this.list = list;
        }

        public static class ListModelXX {
            /**
             * id : 20000
             * key : push
             * description : 收到推送
             * extra_keys : ["push_id","push_type"]
             */

            private long id;
            private String key;
            private String description;
            private List<String> extra_keys;
            private String category;

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public List<String> getExtra_keys() {
                return extra_keys;
            }

            public void setExtra_keys(List<String> extra_keys) {
                this.extra_keys = extra_keys;
            }
        }
    }
}
