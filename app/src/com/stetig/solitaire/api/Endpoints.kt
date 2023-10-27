package com.stetig.solitaire.api

public interface Endpoints {
    companion object {
        const val  SOURCECHANGE_APPROVAL = "SourceChange"
        const val APPROVAL_REQUEST = "ApprovalRequest"
        const val FEEDBACK_FORM = "Feedback"
        const val CREATE_TASK = "CreateTask"
        const val SALES_CALL_TASK = "SalesCallTask"
        const val CAMPAIGN_APPROVAL = "CampaignApproval"
        const val CREATE_TASK_FROM_CALL =  "Createactivity/"
        const val CREATE_TASK_FROM_OPTY =  "Createactivityfromopp/"
        const val UPLOAD_CALL_RECORDING =  "UploadAPI/"
        const val UPDATE_OPPORTUNITIES  =  "UpdateAPI/"
        const val NOTIFICATION_LIST     =  "NotificationList/"
        const val MARK_AS_COMPLETE      =  "Taskcomplete/"
        const val REGISTER_TOKEN        =  "deviceToken/"
        const val MARK_AS_READ          =  "MarkRead/"
        const val APP_VERSION           =  "AppVersion/"
        const val GET_ALL_OPTY          =  "opportunities/005C3000001MbbRIAS"
    }
}