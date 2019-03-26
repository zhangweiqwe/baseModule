/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.wsgwz.myapplication.data;

import java.util.List;

//http://192.168.1.100:8080/app_owner/product/list?token=bd82d2524f414627930bca9dc3dd5d2b
public class TR {


    /**
     * code : 0
     * data : {"product":[{"price":600,"name":"精致镀晶","home_img":"/services/images/licence/20181015/1539583704338_70007.jpg","id":"05e8b11072e04300bf37a02cf90145d9","type":1,"category_img":"/services/images/licence/20180914/1536904936558_27089.jpg"},{"price":300,"name":"道路救援","home_img":"/services/images/licence/20181015/1539583771373_63859.jpg","id":"157e0b09c2a646bf85fb574cc5cd0d00","type":1,"category_img":"/services/images/licence/20180914/1536905139194_32912.jpg"},{"price":1000,"name":"车载加湿器","home_img":"/services/images/licence/20181015/1539583530113_98826.jpg","id":"164297a292ac41328c1419398b056909","type":2,"category_img":"/services/images/licence/20180914/1536911108247_1078.jpg"},{"price":400,"name":"太阳膜","home_img":"/services/images/licence/20181015/1539584656856_72364.jpg","id":"3858f7b35b4b4578af0360c294f1caf4","type":1,"category_img":"/services/images/licence/20180914/1536910245526_46020.jpg"},{"price":120,"name":"小夜灯","home_img":"/services/images/licence/20181015/1539584736316_86644.jpg","id":"4c65b7f867334ef98c73a05f8be51708","type":2,"category_img":"/services/images/licence/20180914/1536911432895_63694.jpg"},{"price":700,"name":"车载冰箱","home_img":"/services/images/licence/20181015/1539584719489_30506.jpg","id":"4e58d52375fc424ab2bb6f659dcb9345","type":2,"category_img":"/services/images/licence/20180914/1536909040263_56354.jpg"},{"price":300,"name":"拖车救援","home_img":"/services/images/licence/20181015/1539584685564_44302.jpg","id":"52e979cea8874c9fb9a46621a346e06b","type":1,"category_img":"/services/images/licence/20180914/1536905449837_801.jpg"},{"price":200,"name":"汽车座套","home_img":"/services/images/licence/20181015/1539584791695_46212.jpg","id":"5faa97ecefab43839077b04fc5a71806","type":2,"category_img":"/services/images/licence/20180914/1536910685018_44054.jpg"},{"price":1200,"name":"智能地锁","home_img":"/services/images/licence/20181015/1539584933882_12069.jpg","id":"65b48f5a4cf84cbeb0f29e46572a490a","type":2,"category_img":"/services/images/licence/20180922/1537597775780_34467.jpg"},{"price":300,"name":"汽车保养","home_img":"/services/images/licence/20181015/1539583738045_12646.jpg","id":"6f5ad00c8a69466ba41f67d18f1bea71","type":1,"category_img":"/services/images/licence/20180914/1536906210188_19688.jpg"},{"price":0.01,"name":"支付测试产品","home_img":"/services/images/licence/20180920/1537424267924_73191.jpg","id":"7243dd283807463faa1f60c3b1f6d740","type":2,"category_img":"/services/images/licence/20180920/1537424292644_61598.jpg"},{"price":500,"name":"内饰清洁护理","home_img":"/services/images/licence/20181015/1539583691084_28865.jpg","id":"72d5729e1014428a9b649262215112a4","type":1,"category_img":"/services/images/licence/20180914/1536907480959_19622.jpg"},{"price":50,"name":"普通洗车","home_img":"/services/images/licence/20181015/1539583656991_27204.jpg","id":"73a949cbac0543339aa209c1b20404a8","type":1,"category_img":"/services/images/licence/20180914/1536908136094_26844.jpg"},{"price":2000,"name":"智能盒 子","home_img":"/services/images/licence/20181015/1539585000436_49078.jpg","id":"92e4757c35274bf296587881b52e53c7","type":2,"category_img":"/services/images/licence/20180914/1536909878085_56742.jpg"},{"price":0.01,"name":"支付产品服务","home_img":"/services/images/licence/20180922/1537595622085_27722.jpg","id":"a0aba57424874ca198eb843f5755b6b8","type":1,"category_img":"/services/images/licence/20180922/1537595634508_86836.jpg"},{"price":80,"name":"车载U盘","home_img":"/services/images/licence/20181015/1539584808630_1563.jpg","id":"aeeb961743d44f9ea2154d9ecd008999","type":2,"category_img":"/services/images/licence/20180922/1537597739172_50005.jpg"},{"price":80,"name":"汽车抱枕","home_img":"/services/images/licence/20181015/1539583564133_78007.jpg","id":"b072d064901f4b5c8f265546f48b3558","type":2,"category_img":"/services/images/licence/20180914/1536910831467_18656.jpg"},{"price":500,"name":"软包脚垫","home_img":"/services/images/licence/20181015/1539585073954_11198.jpg","id":"b219bdfffd7a44518f121d09d693cb7d","type":1,"category_img":"/services/images/licence/20180914/1536910928721_81694.jpg"},{"price":2000,"name":"指纹锁","home_img":"/services/images/licence/20181015/1539585089048_65694.jpg","id":"b3686e9ad2204dc4be62b9fe9dbe6034","type":2,"category_img":"/services/images/licence/20180914/1536911606910_68174.jpg"},{"price":158,"name":"精致洗车","home_img":"/services/images/licence/20181015/1539583667640_10051.jpg","id":"bbaaac58784045528f2234dc70b57d39","type":1,"category_img":"/services/images/licence/20180914/1536907946929_73775.jpg"},{"price":150,"name":"车载香水","home_img":"/services/images/licence/20181015/1539585016061_4068.jpg","id":"c005ec011ce14f33bb5d52bfb95f3f4d","type":2,"category_img":"/services/images/licence/20180922/1537597759594_81284.jpg"},{"price":500,"name":"吸尘器","home_img":"/services/images/licence/20181015/1539583610680_59859.jpg","id":"db66945c97f14fc98da8851a012296ef","type":2,"category_img":"/services/images/licence/20180922/1537597716302_77476.jpg"},{"price":800,"name":"封釉","home_img":"/services/images/licence/20181015/1539585132793_25446.jpg","id":"df99285944df414184a7615ffb634cd2","type":1,"category_img":"/services/images/licence/20180914/1536906854180_13966.jpg"},{"price":400,"name":"镀膜","home_img":"/services/images/licence/20181015/1539594492502_45340.jpg","id":"e3c399fa52dd4948b170038b27df93cd","type":1,"category_img":"/services/images/licence/20180914/1536906609906_18787.jpg"},{"price":500,"name":"抛光打蜡","home_img":"/services/images/licence/20181015/1539585145539_57161.jpg","id":"e44f107bc70d40e4aa7e75c6dc3e859f","type":1,"category_img":"/services/images/licence/20180914/1536907703002_84840.jpg"},{"price":600,"name":"空调养护","home_img":"/services/images/licence/20181015/1539585052989_62116.jpg","id":"ed785419ea784743bda60e22227dba0c","type":1,"category_img":"/services/images/licence/20180914/1536905820584_20019.jpg"}],"recommendProduct":[{"img":"/services/images/ticket/20181015/1539584026936_1263.jpg","name":"车载音响","id":"910e943127164215a66ac246e4b46a66","type":2}]}
     */

    public String code;
    //public String data;
    public DataBean data;

   /* private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }*/

    public static class DataBean {
        private List<ProductBean> product;
        private List<RecommendProductBean> recommendProduct;

        public List<ProductBean> getProduct() {
            return product;
        }

        public void setProduct(List<ProductBean> product) {
            this.product = product;
        }

        public List<RecommendProductBean> getRecommendProduct() {
            return recommendProduct;
        }

        public void setRecommendProduct(List<RecommendProductBean> recommendProduct) {
            this.recommendProduct = recommendProduct;
        }

        public static class ProductBean {
            /**
             * price : 600.0
             * name : 精致镀晶
             * home_img : /services/images/licence/20181015/1539583704338_70007.jpg
             * id : 05e8b11072e04300bf37a02cf90145d9
             * type : 1
             * category_img : /services/images/licence/20180914/1536904936558_27089.jpg
             */

            private double price;
            private String name;
            private String home_img;
            private String id;
            private int type;
            private String category_img;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHome_img() {
                return home_img;
            }

            public void setHome_img(String home_img) {
                this.home_img = home_img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCategory_img() {
                return category_img;
            }

            public void setCategory_img(String category_img) {
                this.category_img = category_img;
            }
        }

        public static class RecommendProductBean {
            /**
             * img : /services/images/ticket/20181015/1539584026936_1263.jpg
             * name : 车载音响
             * id : 910e943127164215a66ac246e4b46a66
             * type : 2
             */

            private String img;
            private String name;
            private String id;
            private int type;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "product=" + product +
                    ", recommendProduct=" + recommendProduct +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TR{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
