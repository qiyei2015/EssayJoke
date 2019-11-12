package com.qiyei.essayjoke.model;

import java.util.List;

/**
 * Email 240336124@qq.com
 * Created by qiyei2015 on 2017/3/5.
 * Version 1.0
 * Description:
 */
public class DiscoverListResult {

    private RotateBannerBean rotate_banner;
    private CategoriesBean categories;
    private GodCommentBean god_comment;
    private List<MyTopCategoryListBean> my_top_category_list;
    private List<MyCategoryListBean> my_category_list;

    public RotateBannerBean getRotate_banner() {
        return rotate_banner;
    }

    public void setRotate_banner(RotateBannerBean rotate_banner) {
        this.rotate_banner = rotate_banner;
    }

    public CategoriesBean getCategories() {
        return categories;
    }

    public void setCategories(CategoriesBean categories) {
        this.categories = categories;
    }

    public GodCommentBean getGod_comment() {
        return god_comment;
    }

    public void setGod_comment(GodCommentBean god_comment) {
        this.god_comment = god_comment;
    }

    public List<MyTopCategoryListBean> getMy_top_category_list() {
        return my_top_category_list;
    }

    public void setMy_top_category_list(List<MyTopCategoryListBean> my_top_category_list) {
        this.my_top_category_list = my_top_category_list;
    }

    public List<MyCategoryListBean> getMy_category_list() {
        return my_category_list;
    }

    public void setMy_category_list(List<MyCategoryListBean> my_category_list) {
        this.my_category_list = my_category_list;
    }

    public static class RotateBannerBean {
        /**
         * count : 0
         * banners : []
         */

        private int count;
        private List<BannersBean> banners;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public static class BannersBean {
            private String schema_url;

            private BannerUrlBean banner_url;
            private List<?> target_users;

            public String getSchema_url() {
                return schema_url;
            }

            public void setSchema_url(String schema_url) {
                this.schema_url = schema_url;
            }

            public BannerUrlBean getBanner_url() {
                return banner_url;
            }

            public void setBanner_url(BannerUrlBean banner_url) {
                this.banner_url = banner_url;
            }

            public List<?> getTarget_users() {
                return target_users;
            }

            public void setTarget_users(List<?> target_users) {
                this.target_users = target_users;
            }

            public static class BannerUrlBean {
                private String title;
                private String uri;
                private int height;
                private int width;
                private int id;
                /**
                 * url : http://p9.pstatp.com/origin/ef400087b7d7fbdec85
                 */

                private List<UrlListBean> url_list;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUri() {
                    return uri;
                }

                public void setUri(String uri) {
                    this.uri = uri;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public List<UrlListBean> getUrl_list() {
                    return url_list;
                }

                public void setUrl_list(List<UrlListBean> url_list) {
                    this.url_list = url_list;
                }

                public static class UrlListBean {
                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }

    public static class CategoriesBean {

        private String name;
        private int priority;
        private int category_count;
        private String intro;
        private int id;
        private Object icon;
        private List<CategoryListBean> category_list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getCategory_count() {
            return category_count;
        }

        public void setCategory_count(int category_count) {
            this.category_count = category_count;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getIcon() {
            return icon;
        }

        public void setIcon(Object icon) {
            this.icon = icon;
        }

        public List<CategoryListBean> getCategory_list() {
            return category_list;
        }

        public void setCategory_list(List<CategoryListBean> category_list) {
            this.category_list = category_list;
        }

        public static class CategoryListBean {


            private String extra;
            private int allow_video;
            private boolean is_recommend;
            private int allow_text_and_pic;
            private String channels;
            private boolean visible;
            private String intro;
            private String top_end_time;
            private int id;
            private int post_rule_id;
            private int share_type;
            private int big_category_id;
            private String icon_url;
            private int dedup;
            private String share_url;
            private String buttons;
            private Object mix_weight;
            private int type;
            private int today_updates;
            private int status;
            private int allow_text;
            private int allow_gif;
            private boolean is_top;
            private boolean is_risk;
            private int allow_multi_image;
            private String placeholder;
            private String icon;
            private int total_updates;
            private int subscribe_count;
            private String name;
            private boolean has_timeliness;
            private String tag;
            private String small_icon_url;
            private String small_icon;
            private String top_start_time;
            private int priority;
            private List<?> material_bar;

            public String getExtra() {
                return extra;
            }

            public void setExtra(String extra) {
                this.extra = extra;
            }

            public int getAllow_video() {
                return allow_video;
            }

            public void setAllow_video(int allow_video) {
                this.allow_video = allow_video;
            }

            public boolean isIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(boolean is_recommend) {
                this.is_recommend = is_recommend;
            }

            public int getAllow_text_and_pic() {
                return allow_text_and_pic;
            }

            public void setAllow_text_and_pic(int allow_text_and_pic) {
                this.allow_text_and_pic = allow_text_and_pic;
            }

            public String getChannels() {
                return channels;
            }

            public void setChannels(String channels) {
                this.channels = channels;
            }

            public boolean isVisible() {
                return visible;
            }

            public void setVisible(boolean visible) {
                this.visible = visible;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getTop_end_time() {
                return top_end_time;
            }

            public void setTop_end_time(String top_end_time) {
                this.top_end_time = top_end_time;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPost_rule_id() {
                return post_rule_id;
            }

            public void setPost_rule_id(int post_rule_id) {
                this.post_rule_id = post_rule_id;
            }

            public int getShare_type() {
                return share_type;
            }

            public void setShare_type(int share_type) {
                this.share_type = share_type;
            }

            public int getBig_category_id() {
                return big_category_id;
            }

            public void setBig_category_id(int big_category_id) {
                this.big_category_id = big_category_id;
            }

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public int getDedup() {
                return dedup;
            }

            public void setDedup(int dedup) {
                this.dedup = dedup;
            }

            public String getShare_url() {
                return share_url;
            }

            public void setShare_url(String share_url) {
                this.share_url = share_url;
            }

            public String getButtons() {
                return buttons;
            }

            public void setButtons(String buttons) {
                this.buttons = buttons;
            }

            public Object getMix_weight() {
                return mix_weight;
            }

            public void setMix_weight(Object mix_weight) {
                this.mix_weight = mix_weight;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getToday_updates() {
                return today_updates;
            }

            public void setToday_updates(int today_updates) {
                this.today_updates = today_updates;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getAllow_text() {
                return allow_text;
            }

            public void setAllow_text(int allow_text) {
                this.allow_text = allow_text;
            }

            public int getAllow_gif() {
                return allow_gif;
            }

            public void setAllow_gif(int allow_gif) {
                this.allow_gif = allow_gif;
            }

            public boolean isIs_top() {
                return is_top;
            }

            public void setIs_top(boolean is_top) {
                this.is_top = is_top;
            }

            public boolean isIs_risk() {
                return is_risk;
            }

            public void setIs_risk(boolean is_risk) {
                this.is_risk = is_risk;
            }

            public int getAllow_multi_image() {
                return allow_multi_image;
            }

            public void setAllow_multi_image(int allow_multi_image) {
                this.allow_multi_image = allow_multi_image;
            }

            public String getPlaceholder() {
                return placeholder;
            }

            public void setPlaceholder(String placeholder) {
                this.placeholder = placeholder;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getTotal_updates() {
                return total_updates;
            }

            public void setTotal_updates(int total_updates) {
                this.total_updates = total_updates;
            }

            public int getSubscribe_count() {
                return subscribe_count;
            }

            public void setSubscribe_count(int subscribe_count) {
                this.subscribe_count = subscribe_count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isHas_timeliness() {
                return has_timeliness;
            }

            public void setHas_timeliness(boolean has_timeliness) {
                this.has_timeliness = has_timeliness;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getSmall_icon_url() {
                return small_icon_url;
            }

            public void setSmall_icon_url(String small_icon_url) {
                this.small_icon_url = small_icon_url;
            }

            public String getSmall_icon() {
                return small_icon;
            }

            public void setSmall_icon(String small_icon) {
                this.small_icon = small_icon;
            }

            public String getTop_start_time() {
                return top_start_time;
            }

            public void setTop_start_time(String top_start_time) {
                this.top_start_time = top_start_time;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public List<?> getMaterial_bar() {
                return material_bar;
            }

            public void setMaterial_bar(List<?> material_bar) {
                this.material_bar = material_bar;
            }
        }
    }

    public static class GodCommentBean {
        /**
         * count : 0
         * intro : 简介
         * name : 神评论
         * icon : http://s0.pstatp.com/site/image/joke_zone/commenticon_discover@2x.png
         */

        private int count;
        private String intro;
        private String name;
        private String icon;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public static class MyTopCategoryListBean {


        private String extra;
        private int allow_video;
        private boolean is_recommend;
        private int allow_text_and_pic;
        private String channels;
        private boolean visible;
        private String intro;
        private String top_end_time;
        private int id;
        private int post_rule_id;
        private int share_type;
        private int big_category_id;
        private String icon_url;
        private int dedup;
        private String share_url;
        private String buttons;
        private Object mix_weight;
        private int type;
        private int today_updates;
        private int status;
        private int allow_text;
        private int allow_gif;
        private boolean is_top;
        private boolean is_risk;
        private int allow_multi_image;
        private String placeholder;
        private String icon;
        private int total_updates;
        private int subscribe_count;
        private String name;
        private boolean has_timeliness;
        private String tag;
        private String small_icon_url;
        private String small_icon;
        private String top_start_time;
        private int priority;
        private List<?> material_bar;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public int getAllow_video() {
            return allow_video;
        }

        public void setAllow_video(int allow_video) {
            this.allow_video = allow_video;
        }

        public boolean isIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(boolean is_recommend) {
            this.is_recommend = is_recommend;
        }

        public int getAllow_text_and_pic() {
            return allow_text_and_pic;
        }

        public void setAllow_text_and_pic(int allow_text_and_pic) {
            this.allow_text_and_pic = allow_text_and_pic;
        }

        public String getChannels() {
            return channels;
        }

        public void setChannels(String channels) {
            this.channels = channels;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTop_end_time() {
            return top_end_time;
        }

        public void setTop_end_time(String top_end_time) {
            this.top_end_time = top_end_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPost_rule_id() {
            return post_rule_id;
        }

        public void setPost_rule_id(int post_rule_id) {
            this.post_rule_id = post_rule_id;
        }

        public int getShare_type() {
            return share_type;
        }

        public void setShare_type(int share_type) {
            this.share_type = share_type;
        }

        public int getBig_category_id() {
            return big_category_id;
        }

        public void setBig_category_id(int big_category_id) {
            this.big_category_id = big_category_id;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public int getDedup() {
            return dedup;
        }

        public void setDedup(int dedup) {
            this.dedup = dedup;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getButtons() {
            return buttons;
        }

        public void setButtons(String buttons) {
            this.buttons = buttons;
        }

        public Object getMix_weight() {
            return mix_weight;
        }

        public void setMix_weight(Object mix_weight) {
            this.mix_weight = mix_weight;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getToday_updates() {
            return today_updates;
        }

        public void setToday_updates(int today_updates) {
            this.today_updates = today_updates;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAllow_text() {
            return allow_text;
        }

        public void setAllow_text(int allow_text) {
            this.allow_text = allow_text;
        }

        public int getAllow_gif() {
            return allow_gif;
        }

        public void setAllow_gif(int allow_gif) {
            this.allow_gif = allow_gif;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public boolean isIs_risk() {
            return is_risk;
        }

        public void setIs_risk(boolean is_risk) {
            this.is_risk = is_risk;
        }

        public int getAllow_multi_image() {
            return allow_multi_image;
        }

        public void setAllow_multi_image(int allow_multi_image) {
            this.allow_multi_image = allow_multi_image;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getTotal_updates() {
            return total_updates;
        }

        public void setTotal_updates(int total_updates) {
            this.total_updates = total_updates;
        }

        public int getSubscribe_count() {
            return subscribe_count;
        }

        public void setSubscribe_count(int subscribe_count) {
            this.subscribe_count = subscribe_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isHas_timeliness() {
            return has_timeliness;
        }

        public void setHas_timeliness(boolean has_timeliness) {
            this.has_timeliness = has_timeliness;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getSmall_icon_url() {
            return small_icon_url;
        }

        public void setSmall_icon_url(String small_icon_url) {
            this.small_icon_url = small_icon_url;
        }

        public String getSmall_icon() {
            return small_icon;
        }

        public void setSmall_icon(String small_icon) {
            this.small_icon = small_icon;
        }

        public String getTop_start_time() {
            return top_start_time;
        }

        public void setTop_start_time(String top_start_time) {
            this.top_start_time = top_start_time;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public List<?> getMaterial_bar() {
            return material_bar;
        }

        public void setMaterial_bar(List<?> material_bar) {
            this.material_bar = material_bar;
        }
    }

    public static class MyCategoryListBean {

        private String extra;
        private int allow_video;
        private boolean is_recommend;
        private int allow_text_and_pic;
        private String channels;
        private boolean visible;
        private String intro;
        private String top_end_time;
        private int id;
        private int post_rule_id;
        private int share_type;
        private int big_category_id;
        private String icon_url;
        private int dedup;
        private String share_url;
        private String buttons;
        private Object mix_weight;
        private int type;
        private int today_updates;
        private int status;
        private int allow_text;
        private int allow_gif;
        private boolean is_top;
        private boolean is_risk;
        private int allow_multi_image;
        private String placeholder;
        private String icon;
        private int total_updates;
        private int subscribe_count;
        private String name;
        private boolean has_timeliness;
        private String tag;
        private String small_icon_url;
        private String small_icon;
        private String top_start_time;
        private int priority;
        private List<?> material_bar;

        public String getExtra() {
            return extra;
        }

        public void setExtra(String extra) {
            this.extra = extra;
        }

        public int getAllow_video() {
            return allow_video;
        }

        public void setAllow_video(int allow_video) {
            this.allow_video = allow_video;
        }

        public boolean isIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(boolean is_recommend) {
            this.is_recommend = is_recommend;
        }

        public int getAllow_text_and_pic() {
            return allow_text_and_pic;
        }

        public void setAllow_text_and_pic(int allow_text_and_pic) {
            this.allow_text_and_pic = allow_text_and_pic;
        }

        public String getChannels() {
            return channels;
        }

        public void setChannels(String channels) {
            this.channels = channels;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getTop_end_time() {
            return top_end_time;
        }

        public void setTop_end_time(String top_end_time) {
            this.top_end_time = top_end_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPost_rule_id() {
            return post_rule_id;
        }

        public void setPost_rule_id(int post_rule_id) {
            this.post_rule_id = post_rule_id;
        }

        public int getShare_type() {
            return share_type;
        }

        public void setShare_type(int share_type) {
            this.share_type = share_type;
        }

        public int getBig_category_id() {
            return big_category_id;
        }

        public void setBig_category_id(int big_category_id) {
            this.big_category_id = big_category_id;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public int getDedup() {
            return dedup;
        }

        public void setDedup(int dedup) {
            this.dedup = dedup;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        public String getButtons() {
            return buttons;
        }

        public void setButtons(String buttons) {
            this.buttons = buttons;
        }

        public Object getMix_weight() {
            return mix_weight;
        }

        public void setMix_weight(Object mix_weight) {
            this.mix_weight = mix_weight;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getToday_updates() {
            return today_updates;
        }

        public void setToday_updates(int today_updates) {
            this.today_updates = today_updates;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAllow_text() {
            return allow_text;
        }

        public void setAllow_text(int allow_text) {
            this.allow_text = allow_text;
        }

        public int getAllow_gif() {
            return allow_gif;
        }

        public void setAllow_gif(int allow_gif) {
            this.allow_gif = allow_gif;
        }

        public boolean isIs_top() {
            return is_top;
        }

        public void setIs_top(boolean is_top) {
            this.is_top = is_top;
        }

        public boolean isIs_risk() {
            return is_risk;
        }

        public void setIs_risk(boolean is_risk) {
            this.is_risk = is_risk;
        }

        public int getAllow_multi_image() {
            return allow_multi_image;
        }

        public void setAllow_multi_image(int allow_multi_image) {
            this.allow_multi_image = allow_multi_image;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getTotal_updates() {
            return total_updates;
        }

        public void setTotal_updates(int total_updates) {
            this.total_updates = total_updates;
        }

        public int getSubscribe_count() {
            return subscribe_count;
        }

        public void setSubscribe_count(int subscribe_count) {
            this.subscribe_count = subscribe_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isHas_timeliness() {
            return has_timeliness;
        }

        public void setHas_timeliness(boolean has_timeliness) {
            this.has_timeliness = has_timeliness;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getSmall_icon_url() {
            return small_icon_url;
        }

        public void setSmall_icon_url(String small_icon_url) {
            this.small_icon_url = small_icon_url;
        }

        public String getSmall_icon() {
            return small_icon;
        }

        public void setSmall_icon(String small_icon) {
            this.small_icon = small_icon;
        }

        public String getTop_start_time() {
            return top_start_time;
        }

        public void setTop_start_time(String top_start_time) {
            this.top_start_time = top_start_time;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public List<?> getMaterial_bar() {
            return material_bar;
        }

        public void setMaterial_bar(List<?> material_bar) {
            this.material_bar = material_bar;
        }
    }
}
