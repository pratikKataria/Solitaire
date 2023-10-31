package com.stetig.solitaire.api

import android.app.Activity
import android.os.Bundle
import com.google.firebase.crashlytics.BuildConfig
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.solitaire.data.*
import com.stetig.solitaire.utils.Utils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by Pratik Kataria on 03-12-2020.
 */
class CommonClassForApi private constructor() {
    fun getUserIdOrEmpty(): String {
        val userAccount = SalesforceSDKManager.getInstance().userAccountManager.currentUser
        val userId = userAccount?.userId
        return userId ?: ""
    }
    companion object {
        private var activity: Activity? = null
        private var commonClassForApi: CommonClassForApi? = null
        fun getInstance(mActivity: Activity?): CommonClassForApi? {
            if (commonClassForApi == null) {
                commonClassForApi = CommonClassForApi()
            }
            this.activity = mActivity
            return commonClassForApi
        }
    }
    fun ApprovalRequest(disposableObserver: DisposableObserver<SendApprocalRequestResponse>, sendCallStatus: SendApprovalRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.sendApprovalRequest(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SendApprocalRequestResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: SendApprocalRequestResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun updateSalesManagerStatus(disposableObserver: DisposableObserver<SMStatusResponse>, smStatusRequest: SMStatusRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.updateSMActivity(smStatusRequest, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SMStatusResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: SMStatusResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun CampaignApprovalRequest(disposableObserver: DisposableObserver<SendCampaignApprovalRequestResponse>, sendCallStatus: SendCampaignApprovalRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.sendCampaignApprovalRequest(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SendCampaignApprovalRequestResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: SendCampaignApprovalRequestResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun SourceChangeApprovalRequest(disposableObserver: DisposableObserver<SendSourceChangeApprovalResponse>, sendCallStatus: SendSourceChangeApprovalRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.sendSourceChangeApprovalRequest(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SendSourceChangeApprovalResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: SendSourceChangeApprovalResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun CpCreationApprovalRequest(disposableObserver: DisposableObserver<CpCreationApprovalResponse>, sendCallStatus: SendCpCreaitonApprovalRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.sendcpcreationApprovalRequest(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CpCreationApprovalResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: CpCreationApprovalResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }


    fun salesCallTaskRequest(disposableObserver: DisposableObserver<CreateTaskFromCallResponse>, sendCallStatus: CallTaskRequest, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.salesCallTaskRequest(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CreateTaskFromCallResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send request please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun FillFeedBackForm(disposableObserver: DisposableObserver<FeedBackResponse>, sendCallStatus: FeedBack, auth: String){
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.feedbackform(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<FeedBackResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: FeedBackResponse) {
                    disposableObserver.onNext(callStatusResponse)
                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to send response please try again...")
                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun createTask(disposableObserver: DisposableObserver<CreateTaskResponse>, sendCallStatus: CreateTask, auth: String){
//        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.createtask(sendCallStatus, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<CreateTaskResponse?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(callStatusResponse: CreateTaskResponse) {
                    disposableObserver.onNext(callStatusResponse)
//                    Utils.hideProgressDialog(activity)
                }

                override fun onError(e: Throwable) {
                    disposableObserver.onError(e)
                    Utils.setToast(activity, "Unable to create task please try again...")
//                    Utils.hideProgressDialog(activity)
                }

                override fun onComplete() {
                    disposableObserver.onComplete()
//                    Utils.hideProgressDialog(activity)
                }
            })
    }

    fun callAttempt(disposableObserver: DisposableObserver<CreateTaskFromCallResponse>, sendCallStatus: CreateTaskFromCall, auth: String) {
        Utils.showProgressDialog(activity)

        val versionCode = BuildConfig.VERSION_CODE
        sendCallStatus.appVersionUser = versionCode.toString()
        RestClient.getInstance().getService()!!.createTask(sendCallStatus, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CreateTaskFromCallResponse?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.setToast(activity, "Unable to create please try again...")
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun createTaskFromOpportunity(disposableObserver: DisposableObserver<CreateTaskFromCallResponse>, sendCallStatus: CreateTaskFromCall, auth: String) {
        Utils.showProgressDialog(activity)

        val versionCode = BuildConfig.VERSION_CODE
        sendCallStatus.appVersionUser = versionCode.toString()


        RestClient.getInstance().getService()!!.createTaskFromOpportunity(sendCallStatus, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<CreateTaskFromCallResponse?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: CreateTaskFromCallResponse) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.setToast(activity, "Unable to create please try again...")
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun markAsComplete(disposableObserver: DisposableObserver<MarkAsCompleteRes>, sendCallStatus: MarkAsCompleteReq, auth: String) {
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.markAsComplete(sendCallStatus, "application/json", auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MarkAsCompleteRes?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: MarkAsCompleteRes) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun updateFeedbackFormStatus(disposableObserver: DisposableObserver<SalesCallTaskResponse>, sendCallStatus: FeedbackFromStatusUpdateRequest, auth: String, bundle: Bundle) {
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.updateFeedbackFromStatus(sendCallStatus,  auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<SalesCallTaskResponse?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: SalesCallTaskResponse) {
                        callStatusResponse.bundle = bundle
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun getNotificationList(disposableObserver: DisposableObserver<ServerNotification>, sendCallStatus: NotificationReqModel, auth: String) {
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.getNotificationList(sendCallStatus, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ServerNotification?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: ServerNotification) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }
    fun getAllOpportunities(disposableObserver: DisposableObserver<AllOpportunityDto>, auth: String) {
        Utils.showProgressDialog(activity)
        RestClient.getInstance().service!!.getAllOpty(getUserIdOrEmpty(),auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<AllOpportunityDto?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: AllOpportunityDto) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun updateOpportunity(disposableObserver: DisposableObserver<UpdateOpportunityRes>, sendCallStatus: UpdateOpportunityReq, auth: String) {
        Utils.showProgressDialog(activity)
        RestClient.getInstance().getService()!!.updateOpportunityS(sendCallStatus, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<UpdateOpportunityRes?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: UpdateOpportunityRes) {
                        disposableObserver.onNext(callStatusResponse)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                        Utils.hideProgressDialog(activity)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                        Utils.hideProgressDialog(activity)
                    }
                })
    }

    fun updateTokenInSalesForce(disposableObserver: DisposableObserver<UpdateTokenRes>, sendCallStatus: UpdateTokenReq?, auth: String?) {
        RestClient.getInstance().service.updateFirebaseToken(sendCallStatus, auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<UpdateTokenRes?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: UpdateTokenRes) {
                        disposableObserver.onNext(callStatusResponse)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                    }
                })
    }
    fun checkDeviceVersion(disposableObserver: DisposableObserver<AppVersionResponse>, auth: String?) {
        RestClient.getInstance().service.checkAppVersion(auth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<AppVersionResponse?> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(callStatusResponse: AppVersionResponse) {
                        disposableObserver.onNext(callStatusResponse)
                    }

                    override fun onError(e: Throwable) {
                        disposableObserver.onError(e)
                    }

                    override fun onComplete() {
                        disposableObserver.onComplete()
                    }
                })
    }

}