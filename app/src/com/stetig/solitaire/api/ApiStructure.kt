package com.stetig.solitaire.api

import com.stetig.solitaire.data.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Pratik Kataria on 03-12-2020.
 */
interface ApiStructure {

    @Headers("Content-Type: application/json")
    @POST(Endpoints.SALES_CALL_TASK)
    fun salesCallTaskRequest(@Body listBody: CallTaskRequest, @Header("Authorization") authToken: String?): Observable<CreateTaskFromCallResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.CP_CREATION_APPROVAL)
    fun sendcpcreationApprovalRequest(@Body listBody: SendCpCreaitonApprovalRequest, @Header("Authorization") authToken: String?): Observable<CpCreationApprovalResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.SOURCECHANGE_APPROVAL)
    fun sendSourceChangeApprovalRequest(@Body listBody: SendSourceChangeApprovalRequest, @Header("Authorization") authToken: String?): Observable<SendSourceChangeApprovalResponse?>
    @Headers("Content-Type: application/json")
    @POST(Endpoints.CAMPAIGN_APPROVAL)
    fun sendCampaignApprovalRequest(@Body listBody: SendCampaignApprovalRequest, @Header("Authorization") authToken: String?): Observable<SendCampaignApprovalRequestResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.CREATE_TASK)
    fun createtask(@Body listBody: CreateTask, @Header("Authorization") authToken: String?): Observable<CreateTaskResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.FEEDBACK_FORM)
    fun feedbackform(@Body listBody: FeedBack, @Header("Authorization") authToken: String?): Observable<FeedBackResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.APPROVAL_REQUEST)
    fun sendApprovalRequest(@Body listBody: SendApprovalRequest, @Header("Authorization") authToken: String?): Observable<SendApprocalRequestResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.CREATE_TASK_FROM_CALL)
    fun createTask(@Body listBody: CreateTaskFromCall, @Header("Authorization") authToken: String?): Observable<CreateTaskFromCallResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.CREATE_TASK_FROM_OPTY)
    fun createTaskFromOpportunity(@Body listBody: CreateTaskFromCall, @Header("Authorization") authToken: String?): Observable<CreateTaskFromCallResponse?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.MARK_AS_COMPLETE)
    fun markAsComplete(@Body obj: MarkAsCompleteReq?, @Header("Content-Type") contentType: String?, @Header("Authorization") authToken: String?): Observable<MarkAsCompleteRes?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.NOTIFICATION_LIST)
    fun getNotificationList(@Body listBody: NotificationReqModel?, @Header("Authorization") authToken: String?): Observable<ServerNotification?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.MARK_AS_READ)
    fun markAsRead(@Body listBody: MarkAsReadModel, @Header("Authorization") authToken: String): Call<MarkAsReadResponse>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.UPDATE_OPPORTUNITIES)
    fun updateOpportunityS(@Body listBody: UpdateOpportunityReq, @Header("Authorization") authToken: String): Observable<UpdateOpportunityRes>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.REGISTER_TOKEN)
    fun updateFirebaseToken(@Body body: UpdateTokenReq?, @Header("Authorization") authToken: String?): Observable<UpdateTokenRes?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.APP_VERSION)
    fun checkAppVersion(@Header("Authorization") authToken: String?): Observable<AppVersionResponse?>

    @Headers("Content-Type: application/json")
    @GET(Endpoints.GET_ALL_OPTY)
    fun getAllOpty(@Path("id") id: String, @Header("Authorization") authToken: String?): Observable<AllOpportunityDto?>

    @Headers("Content-Type: application/json")
    @POST(Endpoints.UPDATE_SM_ACTIVITY)
    fun updateSMActivity(@Body smStatusRequest: SMStatusRequest, @Header("Authorization") authToken: String?): Observable<SMStatusResponse?>

}