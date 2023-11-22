package com.stetig.solitaire.api

import com.salesforce.androidsdk.app.SalesforceSDKManager

public interface Endpoints {

    companion object {
        private val USER_ID: String = "${SalesforceSDKManager.getInstance().userAccountManager.currentUser.userId}"

        const val CP_CREATION_APPROVAL = "CPApproval"
        const val  SOURCECHANGE_APPROVAL = "SourceChange"
        const val APPROVAL_REQUEST = "ApprovalRequest"
        const val FEEDBACK_FORM = "Feedback"
        const val CREATE_TASK = "CreateTask"
        const val SALES_CALL_TASK = "SalesCallTask"
        const val CAMPAIGN_APPROVAL = "CampaignApproval"
        const val MARK_AS_COMPLETE      =  "Markcomplete"
        const val CREATE_TASK_FROM_CALL =  "Createactivity/"
        const val CREATE_TASK_FROM_OPTY =  "Createactivityfromopp/"
        const val UPLOAD_CALL_RECORDING =  "UploadAPI/"
        const val UPDATE_OPPORTUNITIES  =  "UpdateAPI/"
        const val NOTIFICATION_LIST     =  "NotificationList/"
        const val REGISTER_TOKEN        =  "Call_Sync_App/deviceToken/*"
        const val MARK_AS_READ          =  "MarkRead/"
        const val APP_VERSION           =  "appversion"
        const val GET_ALL_OPTY          =  "opportunities/{id}"
        const val UPDATE_SM_ACTIVITY          =  "updateUserActivity"
        const val FEEDBACK_FROM          =  "FeedbackStatus"
        const val CCR_APPROVAL          =  "CCR"
        const val PAYMENT_PLAN_APPROVAL_REQUEST          =  "PaymentPlanApprovalRequest"
    }
}