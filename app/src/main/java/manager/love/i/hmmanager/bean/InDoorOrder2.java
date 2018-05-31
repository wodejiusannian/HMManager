package manager.love.i.hmmanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

// ┏┓　　　┏┓
// ┏┛┻━━━┛┻┓
// ┃　　　　　　　┃ 　
// ┃　　　━　　　┃
// ┃　┳┛　┗┳　┃
// ┃　　　　　　　┃
// ┃　　　┻　　　┃
// ┃　　　　　　　┃
// ┗━┓　　　┏━┛
// ┃　　　┃ 神兽保佑　　　　　　　　
// ┃　　　┃ 代码无BUG！
// ┃　　　┗━━━┓
// ┃　　　　　　　┣┓
// ┃　　　　　　　┏┛
// ┗┓┓┏━┳┓┏┛
// ┃┫┫　┃┫┫
// ┗┻┛　┗┻┛
public class InDoorOrder2 {
    /**
     * ret : 0
     * msg : SUCCESS
     * body : [{"concessionCode":"","discountExplain":"","moneyDiscount":"0.00","orderId":"sm_152646601315","orderInfo":[{"acceptTime":"","applyRefuseTime":"","completeTime":"","consigneeAddress":"重庆市重庆市南岸区 茶园南山庆隆高尔夫一组团56\u20144","consigneeName":"信空海","consigneePhone":"18623398277","consigneeTime":"2018-05-17 上午","deleteStatus":"1","deliveryTime":"","evaluateAverage":"","evaluateContent":"","evaluateState":"-1","evaluateTime":"","goodsColor":"","goodsIntroduction":"","goodsName":"上门衣橱管理服务","goodsPicture":"","goodsPrice":"1998","goodsSize":"","id":"2874","moneyPay":"1998.0","moneyTotal":"1998","orderNumber":"1","orderRemarkBuyer":"","orderType":"3","payTime":"2018-05-16 18:20:24","payType":"1","recommendDate":"","recommendUserId":"","recommendUserName":"","refundReason":"","refuseTime":"","sellerCityName":"重庆市","sellerPhone":"18680824822","sellerPicture":"http://hmyc365.net:8084/file/pic/156/15199835249101.jpg","sellerUserGrade":"3","sellerUserId":"156","sellerUserName":"耿巧玲工作室","wayCode":"","wayCompany":"","wayNo":"","wayPhone":""}]}]
     */

    private String ret;
    private String msg;
    private List<BodyBean> body;

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean  implements Parcelable{
        /**
         * concessionCode :
         * discountExplain :
         * moneyDiscount : 0.00
         * orderId : sm_152646601315
         * orderInfo : [{"acceptTime":"","applyRefuseTime":"","completeTime":"","consigneeAddress":"重庆市重庆市南岸区 茶园南山庆隆高尔夫一组团56\u20144","consigneeName":"信空海","consigneePhone":"18623398277","consigneeTime":"2018-05-17 上午","deleteStatus":"1","deliveryTime":"","evaluateAverage":"","evaluateContent":"","evaluateState":"-1","evaluateTime":"","goodsColor":"","goodsIntroduction":"","goodsName":"上门衣橱管理服务","goodsPicture":"","goodsPrice":"1998","goodsSize":"","id":"2874","moneyPay":"1998.0","moneyTotal":"1998","orderNumber":"1","orderRemarkBuyer":"","orderType":"3","payTime":"2018-05-16 18:20:24","payType":"1","recommendDate":"","recommendUserId":"","recommendUserName":"","refundReason":"","refuseTime":"","sellerCityName":"重庆市","sellerPhone":"18680824822","sellerPicture":"http://hmyc365.net:8084/file/pic/156/15199835249101.jpg","sellerUserGrade":"3","sellerUserId":"156","sellerUserName":"耿巧玲工作室","wayCode":"","wayCompany":"","wayNo":"","wayPhone":""}]
         */

        private String concessionCode;
        private String discountExplain;
        private String moneyDiscount;
        private String orderId;
        private List<OrderInfoBean> orderInfo;
        private String buyUserId;
        private String buyUserName;
        private String buyUserPic;

        protected BodyBean(Parcel in) {
            concessionCode = in.readString();
            discountExplain = in.readString();
            moneyDiscount = in.readString();
            orderId = in.readString();
            orderInfo = in.createTypedArrayList(OrderInfoBean.CREATOR);
            buyUserId = in.readString();
            buyUserName = in.readString();
            buyUserPic = in.readString();
        }

        public static final Creator<BodyBean> CREATOR = new Creator<BodyBean>() {
            @Override
            public BodyBean createFromParcel(Parcel in) {
                return new BodyBean(in);
            }

            @Override
            public BodyBean[] newArray(int size) {
                return new BodyBean[size];
            }
        };

        public String getBuyUserId() {
            return buyUserId;
        }

        public void setBuyUserId(String buyUserId) {
            this.buyUserId = buyUserId;
        }

        public String getBuyUserName() {
            return buyUserName;
        }

        public void setBuyUserName(String buyUserName) {
            this.buyUserName = buyUserName;
        }

        public String getBuyUserPic() {
            return buyUserPic;
        }

        public void setBuyUserPic(String buyUserPic) {
            this.buyUserPic = buyUserPic;
        }




        public String getConcessionCode() {
            return concessionCode;
        }

        public void setConcessionCode(String concessionCode) {
            this.concessionCode = concessionCode;
        }

        public String getDiscountExplain() {
            return discountExplain;
        }

        public void setDiscountExplain(String discountExplain) {
            this.discountExplain = discountExplain;
        }

        public String getMoneyDiscount() {
            return moneyDiscount;
        }

        public void setMoneyDiscount(String moneyDiscount) {
            this.moneyDiscount = moneyDiscount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public List<OrderInfoBean> getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(List<OrderInfoBean> orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(concessionCode);
            dest.writeString(discountExplain);
            dest.writeString(moneyDiscount);
            dest.writeString(orderId);
            dest.writeTypedList(orderInfo);
            dest.writeString(buyUserId);
            dest.writeString(buyUserName);
            dest.writeString(buyUserPic);
        }


        public static class OrderInfoBean implements Parcelable{
            /**
             * acceptTime :
             * applyRefuseTime :
             * completeTime :
             * consigneeAddress : 重庆市重庆市南岸区 茶园南山庆隆高尔夫一组团56—4
             * consigneeName : 信空海
             * consigneePhone : 18623398277
             * consigneeTime : 2018-05-17 上午
             * deleteStatus : 1
             * deliveryTime :
             * evaluateAverage :
             * evaluateContent :
             * evaluateState : -1
             * evaluateTime :
             * goodsColor :
             * goodsIntroduction :
             * goodsName : 上门衣橱管理服务
             * goodsPicture :
             * goodsPrice : 1998
             * goodsSize :
             * id : 2874
             * moneyPay : 1998.0
             * moneyTotal : 1998
             * orderNumber : 1
             * orderRemarkBuyer :
             * orderType : 3
             * payTime : 2018-05-16 18:20:24
             * payType : 1
             * recommendDate :
             * recommendUserId :
             * recommendUserName :
             * refundReason :
             * refuseTime :
             * sellerCityName : 重庆市
             * sellerPhone : 18680824822
             * sellerPicture : http://hmyc365.net:8084/file/pic/156/15199835249101.jpg
             * sellerUserGrade : 3
             * sellerUserId : 156
             * sellerUserName : 耿巧玲工作室
             * wayCode :
             * wayCompany :
             * wayNo :
             * wayPhone :
             */

            private String acceptTime;
            private String applyRefuseTime;
            private String completeTime;
            private String consigneeAddress;
            private String consigneeName;
            private String consigneePhone;
            private String consigneeTime;
            private String deleteStatus;
            private String deliveryTime;
            private String evaluateAverage;
            private String evaluateContent;
            private String evaluateState;
            private String evaluateTime;
            private String goodsColor;
            private String goodsIntroduction;
            private String goodsName;
            private String goodsPicture;
            private String goodsPrice;
            private String goodsSize;
            private String id;
            private String moneyPay;
            private String moneyTotal;
            private String orderNumber;
            private String orderRemarkBuyer;
            private String orderType;
            private String payTime;
            private String payType;
            private String recommendDate;
            private String recommendUserId;
            private String recommendUserName;
            private String refundReason;
            private String refuseTime;
            private String sellerCityName;
            private String sellerPhone;
            private String sellerPicture;
            private String sellerUserGrade;
            private String sellerUserId;
            private String sellerUserName;
            private String wayCode;
            private String wayCompany;
            private String wayNo;
            private String wayPhone;
            private String clothesNumber;


            protected OrderInfoBean(Parcel in) {
                acceptTime = in.readString();
                applyRefuseTime = in.readString();
                completeTime = in.readString();
                consigneeAddress = in.readString();
                consigneeName = in.readString();
                consigneePhone = in.readString();
                consigneeTime = in.readString();
                deleteStatus = in.readString();
                deliveryTime = in.readString();
                evaluateAverage = in.readString();
                evaluateContent = in.readString();
                evaluateState = in.readString();
                evaluateTime = in.readString();
                goodsColor = in.readString();
                goodsIntroduction = in.readString();
                goodsName = in.readString();
                goodsPicture = in.readString();
                goodsPrice = in.readString();
                goodsSize = in.readString();
                id = in.readString();
                moneyPay = in.readString();
                moneyTotal = in.readString();
                orderNumber = in.readString();
                orderRemarkBuyer = in.readString();
                orderType = in.readString();
                payTime = in.readString();
                payType = in.readString();
                recommendDate = in.readString();
                recommendUserId = in.readString();
                recommendUserName = in.readString();
                refundReason = in.readString();
                refuseTime = in.readString();
                sellerCityName = in.readString();
                sellerPhone = in.readString();
                sellerPicture = in.readString();
                sellerUserGrade = in.readString();
                sellerUserId = in.readString();
                sellerUserName = in.readString();
                wayCode = in.readString();
                wayCompany = in.readString();
                wayNo = in.readString();
                wayPhone = in.readString();
                clothesNumber = in.readString();
            }

            public static final Creator<OrderInfoBean> CREATOR = new Creator<OrderInfoBean>() {
                @Override
                public OrderInfoBean createFromParcel(Parcel in) {
                    return new OrderInfoBean(in);
                }

                @Override
                public OrderInfoBean[] newArray(int size) {
                    return new OrderInfoBean[size];
                }
            };

            public String getClothesNumber() {
                return clothesNumber;
            }

            public void setClothesNumber(String clothesNumber) {
                this.clothesNumber = clothesNumber;
            }



            public String getAcceptTime() {
                return acceptTime;
            }

            public void setAcceptTime(String acceptTime) {
                this.acceptTime = acceptTime;
            }

            public String getApplyRefuseTime() {
                return applyRefuseTime;
            }

            public void setApplyRefuseTime(String applyRefuseTime) {
                this.applyRefuseTime = applyRefuseTime;
            }

            public String getCompleteTime() {
                return completeTime;
            }

            public void setCompleteTime(String completeTime) {
                this.completeTime = completeTime;
            }

            public String getConsigneeAddress() {
                return consigneeAddress;
            }

            public void setConsigneeAddress(String consigneeAddress) {
                this.consigneeAddress = consigneeAddress;
            }

            public String getConsigneeName() {
                return consigneeName;
            }

            public void setConsigneeName(String consigneeName) {
                this.consigneeName = consigneeName;
            }

            public String getConsigneePhone() {
                return consigneePhone;
            }

            public void setConsigneePhone(String consigneePhone) {
                this.consigneePhone = consigneePhone;
            }

            public String getConsigneeTime() {
                return consigneeTime;
            }

            public void setConsigneeTime(String consigneeTime) {
                this.consigneeTime = consigneeTime;
            }

            public String getDeleteStatus() {
                return deleteStatus;
            }

            public void setDeleteStatus(String deleteStatus) {
                this.deleteStatus = deleteStatus;
            }

            public String getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(String deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getEvaluateAverage() {
                return evaluateAverage;
            }

            public void setEvaluateAverage(String evaluateAverage) {
                this.evaluateAverage = evaluateAverage;
            }

            public String getEvaluateContent() {
                return evaluateContent;
            }

            public void setEvaluateContent(String evaluateContent) {
                this.evaluateContent = evaluateContent;
            }

            public String getEvaluateState() {
                return evaluateState;
            }

            public void setEvaluateState(String evaluateState) {
                this.evaluateState = evaluateState;
            }

            public String getEvaluateTime() {
                return evaluateTime;
            }

            public void setEvaluateTime(String evaluateTime) {
                this.evaluateTime = evaluateTime;
            }

            public String getGoodsColor() {
                return goodsColor;
            }

            public void setGoodsColor(String goodsColor) {
                this.goodsColor = goodsColor;
            }

            public String getGoodsIntroduction() {
                return goodsIntroduction;
            }

            public void setGoodsIntroduction(String goodsIntroduction) {
                this.goodsIntroduction = goodsIntroduction;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsPicture() {
                return goodsPicture;
            }

            public void setGoodsPicture(String goodsPicture) {
                this.goodsPicture = goodsPicture;
            }

            public String getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(String goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsSize() {
                return goodsSize;
            }

            public void setGoodsSize(String goodsSize) {
                this.goodsSize = goodsSize;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoneyPay() {
                return moneyPay;
            }

            public void setMoneyPay(String moneyPay) {
                this.moneyPay = moneyPay;
            }

            public String getMoneyTotal() {
                return moneyTotal;
            }

            public void setMoneyTotal(String moneyTotal) {
                this.moneyTotal = moneyTotal;
            }

            public String getOrderNumber() {
                return orderNumber;
            }

            public void setOrderNumber(String orderNumber) {
                this.orderNumber = orderNumber;
            }

            public String getOrderRemarkBuyer() {
                return orderRemarkBuyer;
            }

            public void setOrderRemarkBuyer(String orderRemarkBuyer) {
                this.orderRemarkBuyer = orderRemarkBuyer;
            }

            public String getOrderType() {
                return orderType;
            }

            public void setOrderType(String orderType) {
                this.orderType = orderType;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
            }

            public String getRecommendDate() {
                return recommendDate;
            }

            public void setRecommendDate(String recommendDate) {
                this.recommendDate = recommendDate;
            }

            public String getRecommendUserId() {
                return recommendUserId;
            }

            public void setRecommendUserId(String recommendUserId) {
                this.recommendUserId = recommendUserId;
            }

            public String getRecommendUserName() {
                return recommendUserName;
            }

            public void setRecommendUserName(String recommendUserName) {
                this.recommendUserName = recommendUserName;
            }

            public String getRefundReason() {
                return refundReason;
            }

            public void setRefundReason(String refundReason) {
                this.refundReason = refundReason;
            }

            public String getRefuseTime() {
                return refuseTime;
            }

            public void setRefuseTime(String refuseTime) {
                this.refuseTime = refuseTime;
            }

            public String getSellerCityName() {
                return sellerCityName;
            }

            public void setSellerCityName(String sellerCityName) {
                this.sellerCityName = sellerCityName;
            }

            public String getSellerPhone() {
                return sellerPhone;
            }

            public void setSellerPhone(String sellerPhone) {
                this.sellerPhone = sellerPhone;
            }

            public String getSellerPicture() {
                return sellerPicture;
            }

            public void setSellerPicture(String sellerPicture) {
                this.sellerPicture = sellerPicture;
            }

            public String getSellerUserGrade() {
                return sellerUserGrade;
            }

            public void setSellerUserGrade(String sellerUserGrade) {
                this.sellerUserGrade = sellerUserGrade;
            }

            public String getSellerUserId() {
                return sellerUserId;
            }

            public void setSellerUserId(String sellerUserId) {
                this.sellerUserId = sellerUserId;
            }

            public String getSellerUserName() {
                return sellerUserName;
            }

            public void setSellerUserName(String sellerUserName) {
                this.sellerUserName = sellerUserName;
            }

            public String getWayCode() {
                return wayCode;
            }

            public void setWayCode(String wayCode) {
                this.wayCode = wayCode;
            }

            public String getWayCompany() {
                return wayCompany;
            }

            public void setWayCompany(String wayCompany) {
                this.wayCompany = wayCompany;
            }

            public String getWayNo() {
                return wayNo;
            }

            public void setWayNo(String wayNo) {
                this.wayNo = wayNo;
            }

            public String getWayPhone() {
                return wayPhone;
            }

            public void setWayPhone(String wayPhone) {
                this.wayPhone = wayPhone;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(acceptTime);
                dest.writeString(applyRefuseTime);
                dest.writeString(completeTime);
                dest.writeString(consigneeAddress);
                dest.writeString(consigneeName);
                dest.writeString(consigneePhone);
                dest.writeString(consigneeTime);
                dest.writeString(deleteStatus);
                dest.writeString(deliveryTime);
                dest.writeString(evaluateAverage);
                dest.writeString(evaluateContent);
                dest.writeString(evaluateState);
                dest.writeString(evaluateTime);
                dest.writeString(goodsColor);
                dest.writeString(goodsIntroduction);
                dest.writeString(goodsName);
                dest.writeString(goodsPicture);
                dest.writeString(goodsPrice);
                dest.writeString(goodsSize);
                dest.writeString(id);
                dest.writeString(moneyPay);
                dest.writeString(moneyTotal);
                dest.writeString(orderNumber);
                dest.writeString(orderRemarkBuyer);
                dest.writeString(orderType);
                dest.writeString(payTime);
                dest.writeString(payType);
                dest.writeString(recommendDate);
                dest.writeString(recommendUserId);
                dest.writeString(recommendUserName);
                dest.writeString(refundReason);
                dest.writeString(refuseTime);
                dest.writeString(sellerCityName);
                dest.writeString(sellerPhone);
                dest.writeString(sellerPicture);
                dest.writeString(sellerUserGrade);
                dest.writeString(sellerUserId);
                dest.writeString(sellerUserName);
                dest.writeString(wayCode);
                dest.writeString(wayCompany);
                dest.writeString(wayNo);
                dest.writeString(wayPhone);
                dest.writeString(clothesNumber);
            }
        }
    }
}
