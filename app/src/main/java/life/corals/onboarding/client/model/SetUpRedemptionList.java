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

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SetUpRedemptionList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-20T08:09:40.990Z")
public class SetUpRedemptionList {
  @SerializedName("mer_cb_redeem_id")
  private String merCbRedeemId = null;

  @SerializedName("redeem_activedays")
  private String redeemActivedays = null;

  @SerializedName("redeem_activedt")
  private String redeemActivedt = null;

  @SerializedName("redeem_acttime")
  private String redeemActtime = null;

  @SerializedName("redeem_deposit_amt")
  private String redeemDepositAmt = null;

  @SerializedName("redeem_description")
  private String redeemDescription = null;

  @SerializedName("redeem_endtime")
  private String redeemEndtime = null;

  @SerializedName("redeem_expdt")
  private String redeemExpdt = null;

  @SerializedName("redeem_grps")
  private List<RedeemGrp> redeemGrps = null;

  @SerializedName("redeem_points")
  private String redeemPoints = null;

  @SerializedName("redeem_title")
  private String redeemTitle = null;

  @SerializedName("redeem_type")
  private String redeemType = null;

  @SerializedName("voucher_bg")
  private String voucherBg = null;

  @SerializedName("assigned_voucher_id")
  private String assignedVoucherId = null;

  @SerializedName("is_cust_sharable")
  private Boolean isCustSharable = null;

  @SerializedName("max_redemption_allowed")
  private String maxRedemptionAllowed = null;

  @SerializedName("assigned_voucher_count")
  private String assignedVoucherCount = null;

  public SetUpRedemptionList merCbRedeemId(String merCbRedeemId) {
    this.merCbRedeemId = merCbRedeemId;
    return this;
  }

   /**
   * Get merCbRedeemId
   * @return merCbRedeemId
  **/
   @Schema(description = "")
  public String getMerCbRedeemId() {
    return merCbRedeemId;
  }

  public void setMerCbRedeemId(String merCbRedeemId) {
    this.merCbRedeemId = merCbRedeemId;
  }

  public SetUpRedemptionList redeemActivedays(String redeemActivedays) {
    this.redeemActivedays = redeemActivedays;
    return this;
  }

   /**
   * Get redeemActivedays
   * @return redeemActivedays
  **/
   @Schema(description = "")
  public String getRedeemActivedays() {
    return redeemActivedays;
  }

  public void setRedeemActivedays(String redeemActivedays) {
    this.redeemActivedays = redeemActivedays;
  }

  public SetUpRedemptionList redeemActivedt(String redeemActivedt) {
    this.redeemActivedt = redeemActivedt;
    return this;
  }

   /**
   * Get redeemActivedt
   * @return redeemActivedt
  **/
   @Schema(description = "")
  public String getRedeemActivedt() {
    return redeemActivedt;
  }

  public void setRedeemActivedt(String redeemActivedt) {
    this.redeemActivedt = redeemActivedt;
  }

  public SetUpRedemptionList redeemActtime(String redeemActtime) {
    this.redeemActtime = redeemActtime;
    return this;
  }

   /**
   * Get redeemActtime
   * @return redeemActtime
  **/
   @Schema(description = "")
  public String getRedeemActtime() {
    return redeemActtime;
  }

  public void setRedeemActtime(String redeemActtime) {
    this.redeemActtime = redeemActtime;
  }

  public SetUpRedemptionList redeemDepositAmt(String redeemDepositAmt) {
    this.redeemDepositAmt = redeemDepositAmt;
    return this;
  }

   /**
   * Get redeemDepositAmt
   * @return redeemDepositAmt
  **/
   @Schema(description = "")
  public String getRedeemDepositAmt() {
    return redeemDepositAmt;
  }

  public void setRedeemDepositAmt(String redeemDepositAmt) {
    this.redeemDepositAmt = redeemDepositAmt;
  }

  public SetUpRedemptionList redeemDescription(String redeemDescription) {
    this.redeemDescription = redeemDescription;
    return this;
  }

   /**
   * Get redeemDescription
   * @return redeemDescription
  **/
   @Schema(description = "")
  public String getRedeemDescription() {
    return redeemDescription;
  }

  public void setRedeemDescription(String redeemDescription) {
    this.redeemDescription = redeemDescription;
  }

  public SetUpRedemptionList redeemEndtime(String redeemEndtime) {
    this.redeemEndtime = redeemEndtime;
    return this;
  }

   /**
   * Get redeemEndtime
   * @return redeemEndtime
  **/
   @Schema(description = "")
  public String getRedeemEndtime() {
    return redeemEndtime;
  }

  public void setRedeemEndtime(String redeemEndtime) {
    this.redeemEndtime = redeemEndtime;
  }

  public SetUpRedemptionList redeemExpdt(String redeemExpdt) {
    this.redeemExpdt = redeemExpdt;
    return this;
  }

   /**
   * Get redeemExpdt
   * @return redeemExpdt
  **/
   @Schema(description = "")
  public String getRedeemExpdt() {
    return redeemExpdt;
  }

  public void setRedeemExpdt(String redeemExpdt) {
    this.redeemExpdt = redeemExpdt;
  }

  public SetUpRedemptionList redeemGrps(List<RedeemGrp> redeemGrps) {
    this.redeemGrps = redeemGrps;
    return this;
  }

  public SetUpRedemptionList addRedeemGrpsItem(RedeemGrp redeemGrpsItem) {
    if (this.redeemGrps == null) {
      this.redeemGrps = new ArrayList<RedeemGrp>();
    }
    this.redeemGrps.add(redeemGrpsItem);
    return this;
  }

   /**
   * Get redeemGrps
   * @return redeemGrps
  **/
   @Schema(description = "")
  public List<RedeemGrp> getRedeemGrps() {
    return redeemGrps;
  }

  public void setRedeemGrps(List<RedeemGrp> redeemGrps) {
    this.redeemGrps = redeemGrps;
  }

  public SetUpRedemptionList redeemPoints(String redeemPoints) {
    this.redeemPoints = redeemPoints;
    return this;
  }

   /**
   * Get redeemPoints
   * @return redeemPoints
  **/
   @Schema(description = "")
  public String getRedeemPoints() {
    return redeemPoints;
  }

  public void setRedeemPoints(String redeemPoints) {
    this.redeemPoints = redeemPoints;
  }

  public SetUpRedemptionList redeemTitle(String redeemTitle) {
    this.redeemTitle = redeemTitle;
    return this;
  }

   /**
   * Get redeemTitle
   * @return redeemTitle
  **/
   @Schema(description = "")
  public String getRedeemTitle() {
    return redeemTitle;
  }

  public void setRedeemTitle(String redeemTitle) {
    this.redeemTitle = redeemTitle;
  }

  public SetUpRedemptionList redeemType(String redeemType) {
    this.redeemType = redeemType;
    return this;
  }

   /**
   * Get redeemType
   * @return redeemType
  **/
   @Schema(description = "")
  public String getRedeemType() {
    return redeemType;
  }

  public void setRedeemType(String redeemType) {
    this.redeemType = redeemType;
  }

  public SetUpRedemptionList voucherBg(String voucherBg) {
    this.voucherBg = voucherBg;
    return this;
  }

   /**
   * Get voucherBg
   * @return voucherBg
  **/
   @Schema(description = "")
  public String getVoucherBg() {
    return voucherBg;
  }

  public void setVoucherBg(String voucherBg) {
    this.voucherBg = voucherBg;
  }

  public SetUpRedemptionList assignedVoucherId(String assignedVoucherId) {
    this.assignedVoucherId = assignedVoucherId;
    return this;
  }

   /**
   * Get assignedVoucherId
   * @return assignedVoucherId
  **/
   @Schema(description = "")
  public String getAssignedVoucherId() {
    return assignedVoucherId;
  }

  public void setAssignedVoucherId(String assignedVoucherId) {
    this.assignedVoucherId = assignedVoucherId;
  }

  public SetUpRedemptionList isCustSharable(Boolean isCustSharable) {
    this.isCustSharable = isCustSharable;
    return this;
  }

   /**
   * Get isCustSharable
   * @return isCustSharable
  **/
   @Schema(description = "")
  public Boolean isIsCustSharable() {
    return isCustSharable;
  }

  public void setIsCustSharable(Boolean isCustSharable) {
    this.isCustSharable = isCustSharable;
  }

  public SetUpRedemptionList maxRedemptionAllowed(String maxRedemptionAllowed) {
    this.maxRedemptionAllowed = maxRedemptionAllowed;
    return this;
  }

   /**
   * Get maxRedemptionAllowed
   * @return maxRedemptionAllowed
  **/
   @Schema(description = "")
  public String getMaxRedemptionAllowed() {
    return maxRedemptionAllowed;
  }

  public void setMaxRedemptionAllowed(String maxRedemptionAllowed) {
    this.maxRedemptionAllowed = maxRedemptionAllowed;
  }

  public SetUpRedemptionList assignedVoucherCount(String assignedVoucherCount) {
    this.assignedVoucherCount = assignedVoucherCount;
    return this;
  }

   /**
   * Get assignedVoucherCount
   * @return assignedVoucherCount
  **/
   @Schema(description = "")
  public String getAssignedVoucherCount() {
    return assignedVoucherCount;
  }

  public void setAssignedVoucherCount(String assignedVoucherCount) {
    this.assignedVoucherCount = assignedVoucherCount;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SetUpRedemptionList setUpRedemptionList = (SetUpRedemptionList) o;
    return Objects.equals(this.merCbRedeemId, setUpRedemptionList.merCbRedeemId) &&
        Objects.equals(this.redeemActivedays, setUpRedemptionList.redeemActivedays) &&
        Objects.equals(this.redeemActivedt, setUpRedemptionList.redeemActivedt) &&
        Objects.equals(this.redeemActtime, setUpRedemptionList.redeemActtime) &&
        Objects.equals(this.redeemDepositAmt, setUpRedemptionList.redeemDepositAmt) &&
        Objects.equals(this.redeemDescription, setUpRedemptionList.redeemDescription) &&
        Objects.equals(this.redeemEndtime, setUpRedemptionList.redeemEndtime) &&
        Objects.equals(this.redeemExpdt, setUpRedemptionList.redeemExpdt) &&
        Objects.equals(this.redeemGrps, setUpRedemptionList.redeemGrps) &&
        Objects.equals(this.redeemPoints, setUpRedemptionList.redeemPoints) &&
        Objects.equals(this.redeemTitle, setUpRedemptionList.redeemTitle) &&
        Objects.equals(this.redeemType, setUpRedemptionList.redeemType) &&
        Objects.equals(this.voucherBg, setUpRedemptionList.voucherBg) &&
        Objects.equals(this.assignedVoucherId, setUpRedemptionList.assignedVoucherId) &&
        Objects.equals(this.isCustSharable, setUpRedemptionList.isCustSharable) &&
        Objects.equals(this.maxRedemptionAllowed, setUpRedemptionList.maxRedemptionAllowed) &&
        Objects.equals(this.assignedVoucherCount, setUpRedemptionList.assignedVoucherCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merCbRedeemId, redeemActivedays, redeemActivedt, redeemActtime, redeemDepositAmt, redeemDescription, redeemEndtime, redeemExpdt, redeemGrps, redeemPoints, redeemTitle, redeemType, voucherBg, assignedVoucherId, isCustSharable, maxRedemptionAllowed, assignedVoucherCount);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SetUpRedemptionList {\n");
    
    sb.append("    merCbRedeemId: ").append(toIndentedString(merCbRedeemId)).append("\n");
    sb.append("    redeemActivedays: ").append(toIndentedString(redeemActivedays)).append("\n");
    sb.append("    redeemActivedt: ").append(toIndentedString(redeemActivedt)).append("\n");
    sb.append("    redeemActtime: ").append(toIndentedString(redeemActtime)).append("\n");
    sb.append("    redeemDepositAmt: ").append(toIndentedString(redeemDepositAmt)).append("\n");
    sb.append("    redeemDescription: ").append(toIndentedString(redeemDescription)).append("\n");
    sb.append("    redeemEndtime: ").append(toIndentedString(redeemEndtime)).append("\n");
    sb.append("    redeemExpdt: ").append(toIndentedString(redeemExpdt)).append("\n");
    sb.append("    redeemGrps: ").append(toIndentedString(redeemGrps)).append("\n");
    sb.append("    redeemPoints: ").append(toIndentedString(redeemPoints)).append("\n");
    sb.append("    redeemTitle: ").append(toIndentedString(redeemTitle)).append("\n");
    sb.append("    redeemType: ").append(toIndentedString(redeemType)).append("\n");
    sb.append("    voucherBg: ").append(toIndentedString(voucherBg)).append("\n");
    sb.append("    assignedVoucherId: ").append(toIndentedString(assignedVoucherId)).append("\n");
    sb.append("    isCustSharable: ").append(toIndentedString(isCustSharable)).append("\n");
    sb.append("    maxRedemptionAllowed: ").append(toIndentedString(maxRedemptionAllowed)).append("\n");
    sb.append("    assignedVoucherCount: ").append(toIndentedString(assignedVoucherCount)).append("\n");
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

