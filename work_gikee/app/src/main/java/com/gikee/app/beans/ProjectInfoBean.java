package com.gikee.app.beans;



public class ProjectInfoBean {


    /**
     * headInfo : {"price":"22.38","marketValue":"3100000000","circulationRatio":""}
     * baseInfo : {"enName":"OmiseGo","cnName":"嫩模币","introduction":"What is OmiseGo?OmiseGO i","publishTime":"2017-06-27","marketvalueRatio":"","totalSupply":"","circulation":"","turnover":"","turnoverRatio":"","marketvalueRanking":""}
     * publicInfo : {"status":"已完成","plotform":"ETH (ERC20)","distribution":"9.9% T; 25% O","investor_per":"65.1","sell":"保持不变","start_time":"4","end_time":"2017-06-07","start_price":"OMG/CNC","method":"¥22.11","target":"214万","money":"¥4,740万","avg_price":"6","success_money":"6","count":"6"}
     * relatedLinks : {"browser":["https://ethplorer.io/address/0xd26114cd6ee289accf82350c8d8487fedb8a0c07","https://etherscan.io/token/0xd26114cd6ee289accf82350c8d8487fedb8a0c07"],"website":["https://omisego.network/"],"whitePaper":["https://cdn.omise.co/omg/whitepaper.pdf"]}
     */

    private HeadInfoBean headInfo;
    private BaseProjectInfo baseInfo;
    private PublicInfoBean publicInfo;
    private RelatedLinksBean relatedLink;

    public HeadInfoBean getHeadInfo() {
        return headInfo;
    }

    public void setHeadInfo(HeadInfoBean headInfo) {
        this.headInfo = headInfo;
    }

    public PublicInfoBean getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(PublicInfoBean publicInfo) {
        this.publicInfo = publicInfo;
    }

    public RelatedLinksBean getRelatedLinks() {
        return relatedLink;
    }

    public void setRelatedLinks(RelatedLinksBean relatedLinks) {
        this.relatedLink = relatedLinks;
    }


    public BaseProjectInfo getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseProjectInfo baseInfo) {
        this.baseInfo = baseInfo;
    }
}
