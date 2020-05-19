/*
 * Corals app
 * Corals openAPI file
 *
 * OpenAPI spec version: 0.0.10
 * Contact: gp2wins-corals@yahoo.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package life.corals.onboarding.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-01-21T12:54:04.779Z")
public class Body {
  @SerializedName("biz_display_name")
  private String bizDisplayName = null;

  @SerializedName("biz_namelegal")
  private String bizNamelegal = null;

  @SerializedName("biz_regnno")
  private String bizRegnno = null;

  @SerializedName("cashback_points")
  private List<CashbackPoint> cashbackPoints = null;

  @SerializedName("contact_add")
  private String contactAdd = null;

  @SerializedName("contact_mobile")
  private String contactMobile = null;

  @SerializedName("contact_name")
  private String contactName = null;

  @SerializedName("country_cd")
  private String countryCd = null;

  @SerializedName("country_name")
  private String countryName = null;

  @SerializedName("days_walletexpiry")
  private String daysWalletexpiry = null;

  @SerializedName("devices_req")
  private List<DevicesReq> devicesReq = null;

  @SerializedName("email_add")
  private String emailAdd = null;

  @SerializedName("isforce_merscantopay")
  private Boolean isforceMerscantopay = null;

  @SerializedName("mer_category_id")
  private String merCategoryId = null;

  @SerializedName("mer_competitors")
  private List<String> merCompetitors = null;

  @SerializedName("mer_cur_symbol")
  private String merCurSymbol = null;

  @SerializedName("merchant_outlets")
  private List<MerchantOutlet> merchantOutlets = null;

  @SerializedName("monthly_payday")
  private String monthlyPayday = null;

  @SerializedName("post_code")
  private String postCode = null;

  @SerializedName("redemption_list")
  private List<RedemptionList> redemptionList = null;

/*  @SerializedName("redemption_list")
  private List<SetUpRedemptionList> redemptionList = null;*/

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("short_description")
  private String shortDescription = null;

  @SerializedName("web_path")
  private String webPath = null;

  public Body bizDisplayName(String bizDisplayName) {
    this.bizDisplayName = bizDisplayName;
    return this;
  }

   /**
   * Get bizDisplayName
   * @return bizDisplayName
  **/
  @Schema(description = "")
  public String getBizDisplayName() {
    return bizDisplayName;
  }

  public void setBizDisplayName(String bizDisplayName) {
    this.bizDisplayName = bizDisplayName;
  }

  public Body bizNamelegal(String bizNamelegal) {
    this.bizNamelegal = bizNamelegal;
    return this;
  }

   /**
   * Get bizNamelegal
   * @return bizNamelegal
  **/
   @Schema(description = "")
  public String getBizNamelegal() {
    return bizNamelegal;
  }

  public void setBizNamelegal(String bizNamelegal) {
    this.bizNamelegal = bizNamelegal;
  }

  public Body bizRegnno(String bizRegnno) {
    this.bizRegnno = bizRegnno;
    return this;
  }

   /**
   * Get bizRegnno
   * @return bizRegnno
  **/
   @Schema(description = "")
  public String getBizRegnno() {
    return bizRegnno;
  }

  public void setBizRegnno(String bizRegnno) {
    this.bizRegnno = bizRegnno;
  }

  public Body cashbackPoints(List<CashbackPoint> cashbackPoints) {
    this.cashbackPoints = cashbackPoints;
    return this;
  }

  public Body addCashbackPointsItem(CashbackPoint cashbackPointsItem) {
    if (this.cashbackPoints == null) {
      this.cashbackPoints = new ArrayList<CashbackPoint>();
    }
    this.cashbackPoints.add(cashbackPointsItem);
    return this;
  }

   /**
   * Get cashbackPoints
   * @return cashbackPoints
  **/
   @Schema(description = "")
  public List<CashbackPoint> getCashbackPoints() {
    return cashbackPoints;
  }

  public void setCashbackPoints(List<CashbackPoint> cashbackPoints) {
    this.cashbackPoints = cashbackPoints;
  }

  public Body contactAdd(String contactAdd) {
    this.contactAdd = contactAdd;
    return this;
  }

   /**
   * Get contactAdd
   * @return contactAdd
  **/
   @Schema(description = "")
  public String getContactAdd() {
    return contactAdd;
  }

  public void setContactAdd(String contactAdd) {
    this.contactAdd = contactAdd;
  }

  public Body contactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
    return this;
  }

   /**
   * Get contactMobile
   * @return contactMobile
  **/
   @Schema(description = "")
  public String getContactMobile() {
    return contactMobile;
  }

  public void setContactMobile(String contactMobile) {
    this.contactMobile = contactMobile;
  }

  public Body contactName(String contactName) {
    this.contactName = contactName;
    return this;
  }

   /**
   * Get contactName
   * @return contactName
  **/
   @Schema(description = "")
  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public Body countryCd(String countryCd) {
    this.countryCd = countryCd;
    return this;
  }

   /**
   * Get countryCd
   * @return countryCd
  **/
   @Schema(description = "")
  public String getCountryCd() {
    return countryCd;
  }

  public void setCountryCd(String countryCd) {
    this.countryCd = countryCd;
  }

  public Body countryName(String countryName) {
    this.countryName = countryName;
    return this;
  }

   /**
   * Get countryName
   * @return countryName
  **/
   @Schema(description = "")
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Body daysWalletexpiry(String daysWalletexpiry) {
    this.daysWalletexpiry = daysWalletexpiry;
    return this;
  }

   /**
   * Get daysWalletexpiry
   * @return daysWalletexpiry
  **/
   @Schema(description = "")
  public String getDaysWalletexpiry() {
    return daysWalletexpiry;
  }

  public void setDaysWalletexpiry(String daysWalletexpiry) {
    this.daysWalletexpiry = daysWalletexpiry;
  }

  public Body devicesReq(List<DevicesReq> devicesReq) {
    this.devicesReq = devicesReq;
    return this;
  }

  public Body addDevicesReqItem(DevicesReq devicesReqItem) {
    if (this.devicesReq == null) {
      this.devicesReq = new ArrayList<DevicesReq>();
    }
    this.devicesReq.add(devicesReqItem);
    return this;
  }

   /**
   * Get devicesReq
   * @return devicesReq
  **/
   @Schema(description = "")
  public List<DevicesReq> getDevicesReq() {
    return devicesReq;
  }

  public void setDevicesReq(List<DevicesReq> devicesReq) {
    this.devicesReq = devicesReq;
  }

  public Body emailAdd(String emailAdd) {
    this.emailAdd = emailAdd;
    return this;
  }

   /**
   * Get emailAdd
   * @return emailAdd
  **/
   @Schema(description = "")
  public String getEmailAdd() {
    return emailAdd;
  }

  public void setEmailAdd(String emailAdd) {
    this.emailAdd = emailAdd;
  }

  public Body isforceMerscantopay(Boolean isforceMerscantopay) {
    this.isforceMerscantopay = isforceMerscantopay;
    return this;
  }

   /**
   * Get isforceMerscantopay
   * @return isforceMerscantopay
  **/
   @Schema(description = "")
  public Boolean isIsforceMerscantopay() {
    return isforceMerscantopay;
  }

  public void setIsforceMerscantopay(Boolean isforceMerscantopay) {
    this.isforceMerscantopay = isforceMerscantopay;
  }

  public Body merCategoryId(String merCategoryId) {
    this.merCategoryId = merCategoryId;
    return this;
  }

   /**
   * Get merCategoryId
   * @return merCategoryId
  **/
   @Schema(description = "")
  public String getMerCategoryId() {
    return merCategoryId;
  }

  public void setMerCategoryId(String merCategoryId) {
    this.merCategoryId = merCategoryId;
  }

  public Body merCompetitors(List<String> merCompetitors) {
    this.merCompetitors = merCompetitors;
    return this;
  }

  public Body addMerCompetitorsItem(String merCompetitorsItem) {
    if (this.merCompetitors == null) {
      this.merCompetitors = new ArrayList<String>();
    }
    this.merCompetitors.add(merCompetitorsItem);
    return this;
  }

   /**
   * Get merCompetitors
   * @return merCompetitors
  **/
   @Schema(description = "")
  public List<String> getMerCompetitors() {
    return merCompetitors;
  }

  public void setMerCompetitors(List<String> merCompetitors) {
    this.merCompetitors = merCompetitors;
  }

  public Body merCurSymbol(String merCurSymbol) {
    this.merCurSymbol = merCurSymbol;
    return this;
  }

   /**
   * Get merCurSymbol
   * @return merCurSymbol
  **/
   @Schema(description = "")
  public String getMerCurSymbol() {
    return merCurSymbol;
  }

  public void setMerCurSymbol(String merCurSymbol) {
    this.merCurSymbol = merCurSymbol;
  }

  public Body merchantOutlets(List<MerchantOutlet> merchantOutlets) {
    this.merchantOutlets = merchantOutlets;
    return this;
  }

  public Body addMerchantOutletsItem(MerchantOutlet merchantOutletsItem) {
    if (this.merchantOutlets == null) {
      this.merchantOutlets = new ArrayList<MerchantOutlet>();
    }
    this.merchantOutlets.add(merchantOutletsItem);
    return this;
  }

   /**
   * Get merchantOutlets
   * @return merchantOutlets
  **/
   @Schema(description = "")
  public List<MerchantOutlet> getMerchantOutlets() {
    return merchantOutlets;
  }

  public void setMerchantOutlets(List<MerchantOutlet> merchantOutlets) {
    this.merchantOutlets = merchantOutlets;
  }

  public Body monthlyPayday(String monthlyPayday) {
    this.monthlyPayday = monthlyPayday;
    return this;
  }

   /**
   * Get monthlyPayday
   * @return monthlyPayday
  **/
   @Schema(description = "")
  public String getMonthlyPayday() {
    return monthlyPayday;
  }

  public void setMonthlyPayday(String monthlyPayday) {
    this.monthlyPayday = monthlyPayday;
  }

  public Body postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
   @Schema(description = "")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Body redemptionList(List<RedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
    return this;
  }

  public Body addRedemptionListItem(RedemptionList redemptionListItem) {
    if (this.redemptionList == null) {
      this.redemptionList = new ArrayList<RedemptionList>();
    }
    this.redemptionList.add(redemptionListItem);
    return this;
  }

   /**
   * Get redemptionList
   * @return redemptionList
  **/
   @Schema(description = "")
  public List<RedemptionList> getRedemptionList() {
    return redemptionList;
  }

  public void setRedemptionList(List<RedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
  }

  public Body sessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
    return this;
  }

   /**
   * Get sessiontoken
   * @return sessiontoken
  **/
   @Schema(description = "")
  public String getSessiontoken() {
    return sessiontoken;
  }

  public void setSessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
  }

  public Body shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

   /**
   * Get shortDescription
   * @return shortDescription
  **/
   @Schema(description = "")
  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public Body webPath(String webPath) {
    this.webPath = webPath;
    return this;
  }

   /**
   * Get webPath
   * @return webPath
  **/
   @Schema(description = "")
  public String getWebPath() {
    return webPath;
  }

  public void setWebPath(String webPath) {
    this.webPath = webPath;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body body = (Body) o;
    return Objects.equals(this.bizDisplayName, body.bizDisplayName) &&
        Objects.equals(this.bizNamelegal, body.bizNamelegal) &&
        Objects.equals(this.bizRegnno, body.bizRegnno) &&
        Objects.equals(this.cashbackPoints, body.cashbackPoints) &&
        Objects.equals(this.contactAdd, body.contactAdd) &&
        Objects.equals(this.contactMobile, body.contactMobile) &&
        Objects.equals(this.contactName, body.contactName) &&
        Objects.equals(this.countryCd, body.countryCd) &&
        Objects.equals(this.countryName, body.countryName) &&
        Objects.equals(this.daysWalletexpiry, body.daysWalletexpiry) &&
        Objects.equals(this.devicesReq, body.devicesReq) &&
        Objects.equals(this.emailAdd, body.emailAdd) &&
        Objects.equals(this.isforceMerscantopay, body.isforceMerscantopay) &&
        Objects.equals(this.merCategoryId, body.merCategoryId) &&
        Objects.equals(this.merCompetitors, body.merCompetitors) &&
        Objects.equals(this.merCurSymbol, body.merCurSymbol) &&
        Objects.equals(this.merchantOutlets, body.merchantOutlets) &&
        Objects.equals(this.monthlyPayday, body.monthlyPayday) &&
        Objects.equals(this.postCode, body.postCode) &&
        Objects.equals(this.redemptionList, body.redemptionList) &&
        Objects.equals(this.sessiontoken, body.sessiontoken) &&
        Objects.equals(this.shortDescription, body.shortDescription) &&
        Objects.equals(this.webPath, body.webPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bizDisplayName, bizNamelegal, bizRegnno, cashbackPoints, contactAdd, contactMobile, contactName, countryCd, countryName, daysWalletexpiry, devicesReq, emailAdd, isforceMerscantopay, merCategoryId, merCompetitors, merCurSymbol, merchantOutlets, monthlyPayday, postCode, redemptionList, sessiontoken, shortDescription, webPath);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body {\n");
    
    sb.append("    bizDisplayName: ").append(toIndentedString(bizDisplayName)).append("\n");
    sb.append("    bizNamelegal: ").append(toIndentedString(bizNamelegal)).append("\n");
    sb.append("    bizRegnno: ").append(toIndentedString(bizRegnno)).append("\n");
    sb.append("    cashbackPoints: ").append(toIndentedString(cashbackPoints)).append("\n");
    sb.append("    contactAdd: ").append(toIndentedString(contactAdd)).append("\n");
    sb.append("    contactMobile: ").append(toIndentedString(contactMobile)).append("\n");
    sb.append("    contactName: ").append(toIndentedString(contactName)).append("\n");
    sb.append("    countryCd: ").append(toIndentedString(countryCd)).append("\n");
    sb.append("    countryName: ").append(toIndentedString(countryName)).append("\n");
    sb.append("    daysWalletexpiry: ").append(toIndentedString(daysWalletexpiry)).append("\n");
    sb.append("    devicesReq: ").append(toIndentedString(devicesReq)).append("\n");
    sb.append("    emailAdd: ").append(toIndentedString(emailAdd)).append("\n");
    sb.append("    isforceMerscantopay: ").append(toIndentedString(isforceMerscantopay)).append("\n");
    sb.append("    merCategoryId: ").append(toIndentedString(merCategoryId)).append("\n");
    sb.append("    merCompetitors: ").append(toIndentedString(merCompetitors)).append("\n");
    sb.append("    merCurSymbol: ").append(toIndentedString(merCurSymbol)).append("\n");
    sb.append("    merchantOutlets: ").append(toIndentedString(merchantOutlets)).append("\n");
    sb.append("    monthlyPayday: ").append(toIndentedString(monthlyPayday)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    redemptionList: ").append(toIndentedString(redemptionList)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
    sb.append("    webPath: ").append(toIndentedString(webPath)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

