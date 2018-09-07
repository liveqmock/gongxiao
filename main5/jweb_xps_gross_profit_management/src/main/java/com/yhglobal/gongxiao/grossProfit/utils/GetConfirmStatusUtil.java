package com.yhglobal.gongxiao.grossProfit.utils;

import com.yhglobal.gongxiao.grossProfit.constant.ConfirmStatus;

/**
 * @author 王帅
 */
public class GetConfirmStatusUtil {

    /**
     *  通过应收确认和实收判断 确认状态的方法
     * @param toBeConfirmAmount 未确认额度
     * @param estimatedCouponAmount 应收额度
     * @return
     */
    public  static  Byte updateCouponStatus(Long toBeConfirmAmount, Long estimatedCouponAmount){
        Byte targetCouponStatus = 0; //保存目标应收状态
        if (estimatedCouponAmount > 0) { //应收为正 比如:正常采购
            /**
             * 应收为正的状态判定:
             * (a) 若diff小于等于0 表示已全部确认
             * (b) 若diff大于0, 且待核销金额和初始预估的核销值一致 则表示从未有过核销确认
             * (c) 其他情况认为是 部分核销
             */
            if (toBeConfirmAmount <= 0) { //应收已全部发放
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            }  else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过返利核销确认
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                // 当应收返利为正时,未收 为 非正时 均为全部发放
                // 未收为正且小于 应收 或者 大于应收(即此时确认额度为负数) 则为部分发放
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }
        } else { //应收返利为负: 比如采购退货
            /**
             * 应收返利为负的状态判定:
             * (a) 若diff大于等于0 表示返利已全部确认
             * (b) 若diff小于0, 且待核销金额和初始预估的核销值一致 则表示从未有过返利核销确认
             * (c) 其他情况认为是 部分核销
             */
            if (toBeConfirmAmount >= 0) { //应收已全部发放
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_ALL_ISSUE_NOT_USING.getCode();
            } else if (toBeConfirmAmount.longValue() == estimatedCouponAmount.longValue()){ //从未有过返利核销确认
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_NOT_TO_ISSUE.getCode();
            } else { //其他情况为部分确认
                targetCouponStatus = ConfirmStatus.COUPON_STATUS_PART_OF_ISSUE.getCode();//部分发放
            }
        }
        return targetCouponStatus;

    }
}
