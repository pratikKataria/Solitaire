package com.stetig.solitaire.api

import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.solitaire.utils.Utils

interface Query {
    companion object {
        private val USER_NAME: String = SalesforceSDKManager.getInstance().userAccountManager.currentUser.username
        private val USER_ID: String = "${SalesforceSDKManager.getInstance().userAccountManager.currentUser.userId}"
        const val SITE_VISIT = "Proposed site visit"
        const val FOLLOW_UP = "Follow up"
        const val FACE_TO_FACE = "Aloted site visit"

        const val NEED_ANALYSIS = "Qualification"
        const val NEGOTIATION = "Booking Confirmed"
        const val PROPOSAL = "Closed Lost"

        const val SCHEDULED_SITE_VISIT = "sales Manager Allocated"
        const val ONGOING_SITE_VISIT = "site visit initiated"
        const val PAST_SITE_VISIT = "Feedback submitted"

        const val BIP = "Token Amount Received - BIP"
        const val QUALIFICATION = "Qualification"

        const val RECORD_TYPE_ID_FILTER = "RecordTypeId = '012C3000000Aqq9IAC'"//SB

//        const val RECORD_TYPE_ID_FILTER = "RecordTypeId = '0122t000000XdOC'" //Production
        const val ACTIVE_FILTER = ""
        const val CITY_FILTER = "RDS_City__c = "
        const val SUBJECT_FILTER = "SUBJECT = "
        const val STAGE_NAME_FILTER = "StageName = "
        const val OPPORTUNITY_ID_FILTER = "WhatId = "
        const val OPPORTUNITY_ID_NOT_NULL_FILTER = "WhatId != null"
        const val PROJECT_NAME_FILTER = "Project__r.name = "
        const val STATUS_FILTER = "Status__c = 'Open'"
        const val SITE_VISIT_FILTER = "Site_Visit_Stage__c = "
        const val CLOSE_DATE_FILTER = "CloseDate = NEXT_N_DAYS:7"
        const val CLOSE_DATE_TODAY_FILTER = "Closedate = TODAY"
        const val ACTIVITY_DATE_TODAY_FILTER = "ActivityDate = TODAY"
        const val PROJECT_COLLATERAL_FILTER = "Project_Collaterals_on_App__c = TRUE"
        const val PROJECT_COLLATERAL_ACTIVE = "Is_Active__c = TRUE"
        const val OPPORTUNITY_ACTIVE_FILTER = "Is_Active_basis_close_date__c = true"
        const val ORDER_BY_CREATED_DATE = "ORDER BY CreatedDate DESC"
        const val ORDER_BY_TASK_NAME = "ORDER BY what.name ASC"
        const val ORDER_BY_ACTIVITY_DATE = "ORDER BY ActivityDate DESC"
        const val STAGE_NAME_NOT_EQUAL_LOST_FILTER = "StageName !='Closed Lost'"
        const val WHAT_ID_IN_TASK_FILTER = "whatid in (select id from opportunity)"
        const val STATUS_OPEN_FILTER = "status ='Open'"
        const val ORDER_BY = "ORDER BY "
        const val ASC = " ASC"
        const val DSC = " DESC"
        val USER_NAME_FILTER = "username = ${Utils.buildQueryParameter(USER_NAME)}"
        val OWNER_FILTER = "Owner.username = ${Utils.buildQueryParameter(USER_NAME)}"

        private const val AND = "AND"
        private const val WHERE = "WHERE"
        private const val OR = "OR"

        val USER_ACCOUNT = "SELECT Id, firstName, LastName, Username, Phone, MobilePhone, Email from User $WHERE $USER_NAME_FILTER"
//            "SELECT COUNT(ID) FROM TASK WHERE RecordTypeId = 00DC30000006cdh AND WhatId != null and Call_Proposed_Date_Of_Visit__c != null AND ActivityDate = TODAY"
        val TODAY_TASK_SITE_VISIT_COUNT = "SELECT COUNT(ID) FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Proposed_Date_Of_Visit__c != null $AND $ACTIVITY_DATE_TODAY_FILTER "
        val TODAY_TASK_FOLLOW_UP_COUNT = "SELECT COUNT(ID) FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Attempt_Status__c != null $AND $ACTIVITY_DATE_TODAY_FILTER "
        val TODAY_TASK_FACE_TO_FACE_COUNT = "SELECT COUNT(ID) FROM site_visit__c $WHERE Site_Visit_Stage__c!='Pending Sales Manager Allocation' $AND CreatedDate=Today"

//        SELECT COUNT(ID) FROM TASK WHERE RecordTypeId = '012C3000000Aqq9IAC' AND WhatId != null AND Call_Proposed_Date_Of_Visit__c != null
        val ALL_TASK_SITE_VISIT_COUNT = "SELECT COUNT(ID) FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Proposed_Date_Of_Visit__c != null "
        val ALL_TASK_FOLLOW_UP_COUNT = "SELECT COUNT(ID) FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Attempt_Status__c != null"
        val ALL_TASK_FACE_TO_FACE_COUNT = "SELECT COUNT(ID) FROM site_visit__c $WHERE Site_Visit_Stage__c!='Pending Sales Manager Allocation'"

//      SELECT * from Opportunity WHERE Is_Active_basis_close_date__c = true AND Owner.username = 'chandan.kokul@stetig.in.devs' AND StageName =
        val OPPORTUNITY_LIST = "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, CreatedDate, Account_Email__c, Sales_Call_Attempt_Date__c, Project__r.Name from Opportunity $WHERE $OWNER_FILTER $AND $STAGE_NAME_FILTER"
        val OPPORTUNITY_DETAIL = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, Account_Email__c, CreatedDate, Sales_Call_Description__c, CloseDate,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Configuration__c from Opportunity $WHERE id="
        val ALL_ACTIVE_OPPORTUNITY = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, Account_Email__c, CreatedDate, Sales_Call_Description__c, CloseDate,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Configuration__c from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND Active_New__c = true $ASC"
        val ALL_ACTIVE_OPPORTUNITY_ORDER_BY = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, CreatedDate, Sales_Call_Description__c,Account_Email__c ,CloseDate, Walk_in_Source__c, LeadSource, Configuration__c,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Project__r.Name from Opportunity $WHERE $OWNER_FILTER $AND $STAGE_NAME_NOT_EQUAL_LOST_FILTER $ORDER_BY"
        val EXPIRING_OPPORTUNITY_IN_7_DAYS = "SELECT id, Name, CloseDate, Project__r.Name, StageName, Status__c,  Account_Mobile_Number__c, Sales_Call_Attempt_Date__c from Opportunity $WHERE $OWNER_FILTER $AND $STAGE_NAME_NOT_EQUAL_LOST_FILTER $AND ( $CLOSE_DATE_FILTER $OR $CLOSE_DATE_TODAY_FILTER)"

        val SITE_VISIT_LIST = "SELECT Id,Name,Customer_Name1__c,Type_of_Visit__c, Customer_Name__c, Type_of_Enquiry__c,Opportunity_Project__c,Opportunity_Sourcing_Manager__c,createdDate,Site_Visit_Stage__c from site_visit__c $WHERE $SITE_VISIT_FILTER"

        val SITE_VISIT_DETAIL = "SELECT Property_Type__c,Property_SubType__c,Purpose_of_Purchase__c,Zone__c,Desired_Possession__c,Budget__c from site_visit__c $WHERE id="
        var PROJECT_NAME_FROM_OPPORTUNITY_NUMBER = "SELECT Account_Email_Id__c, Project__c, Account_Mobile_Number__c, Project__r.Name from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND Account_Mobile_Number__c = "


        val PROJECTS_COLLATERAL = "Select Id, RDS_City__c,RDS_State__c,Address__c ,Name FROM Project__c"
        val PROJECTS_COLLATERAL_CITY_FILTER = "SELECT Id ,Name,RDS_City__c,RDS_State__c,Address__c FROM Project__c $WHERE $CITY_FILTER"
        val PROJECT_DETAIL_COLLATERAL = "SELECT Id, Name, Project__c, Collateral_Link__c, Project__r.Name FROM Project_Collateral__c  $WHERE $PROJECT_COLLATERAL_ACTIVE $AND $PROJECT_NAME_FILTER"

        val APPROVAL_LIST_COUNT = "SELECT COUNT(ID) FROM Cost_Sheet__c $WHERE Status__c ="
        val APPROVAL_LIST = "SELECT Id,Opportunity_Name__c,Name, Status__c, Project__r.Name, Tower__r.Name, Project_Unit__r.Name, Opportunity_Name__r.Name, Opportunity_Name__r.Owner.Name FROM Cost_Sheet__c $WHERE User__c = ${Utils.buildQueryParameter(USER_ID)} $AND Status__c ="
        val APPROVAL_TABLE_FIELDS = "Select Id,Base_Rate__c,Base_Rate_Original__c,Total_Amount_for_Unit__c,Infrastructure_Charges__c,Infrastructure_Charges_Original__c,e_IBE_MSEB_Development_Charges__c,IBE_MSEB_Development_Charges_Original__c,Premium_Charges__c,Premium_Charges_Original__c,Floor_Rise__c,Floor_Rise_Original__c,Legal_Charges__c,Legal_Charges_Original__c,X1_Total_Consideration_Value_c_d_e__c,Total_Consideration_Value_Original__c,Total_Consideration_Value_Difference__c,Stamp_Duty_Waived_Off__c,GST_Waived_Off__c,Registration_Charges_Waived_Off__c from Cost_Sheet__c $WHERE Opportunity_Name__c = "

        val CAMPAIGN_APPROVAL_LIST_COUNT = "SELECT COUNT(ID) FROM Campaign $WHERE RecordTypeId = '012C3000000Aqq9IAC' $AND Approval_Status__c="
        val CAMPAIGN_APPROVAL_LIST = "Select Id, Name, Primary_Project__r.Name,Parent.Name,Approval_Status__c from Campaign $WHERE RecordTypeId = '012C30000007DKOIA2' $AND ((Approval_Status__c='Submitted for Approval' $AND Level_1_Approver__c='005C3000001MbbRIAS') $OR (Approval_Status__c='Level 1 Approved' $AND Level_2_Approver__c!=null $AND Level_2_Approver__c='005C3000001MbbRIAS'))"
        val CAMPAIGN_TABLE_FIELDS = "Select Id, Name, Primary_Project__r.Name,Parent.Name,BudgetedCost, Approval_Status__c , StartDate,EndDate from Campaign $WHERE RecordTypeId = '012C30000007DKOIA2' $AND  id = "

        val SEARCH = "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, Sales_Call_Attempt_Date__c, Project__c, Project__r.Name from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND $OWNER_FILTER $AND"
        val TIME_LINE_QUERY = "Select Id, Mobile_Number_Webform__c, Task_Type__c, Subject, ActivityDate, createddate, WhatId, status, RecordTypeId FROM Task $WHERE "
//        Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task WHERE whatid in (select id from opportunity) AND RecordTypeId = '012C3000000Aqq9IAC'

        val PROPOSED_SITE_VISIT_LIST_TODAY = "SELECT ID, what.Name, what.Id, CreatedDate FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Proposed_Date_Of_Visit__c != null $AND $ACTIVITY_DATE_TODAY_FILTER"
        val PROPOSED_SITE_VISIT_LIST = "SELECT ID,what.Name, what.Id,CreatedDate FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Proposed_Date_Of_Visit__c != null"

        val FOLLOW_UP_TODAY = "SELECT ID,what.Name, what.Id,CreatedDate FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Attempt_Status__c != null $AND $ACTIVITY_DATE_TODAY_FILTER"
        val FOLLOW_UP_LIST = "SELECT ID,what.Name, what.Id,CreatedDate FROM TASK $WHERE $RECORD_TYPE_ID_FILTER $AND $OPPORTUNITY_ID_NOT_NULL_FILTER $AND Call_Attempt_Status__c != null"

        val ALOTED_SITE_VISIT_LIST_TODAY = "SELECT ID,Site_Visit_Stage__c,Customer_Name__c,Project__r.Name,Mobile_No2__c  FROM site_visit__c $WHERE Site_Visit_Stage__c!='Pending Sales Manager Allocation' $AND CreatedDate=Today"
        val ALOTED_SITE_VISIT_LIST = "SELECT ID,Site_Visit_Stage__c,Customer_Name__c,Project__r.Name,Mobile_No2__c  FROM site_visit__c $WHERE Site_Visit_Stage__c!='Pending Sales Manager Allocation'"

        val PROJECT_LINK_FETCH = "SELECT Id, ContentDocumentId FROM ContentDocumentLink $WHERE linkedEntityId ="
        val PROJECT_LINK = "SELECT Id, LatestPublishedVersion.Title,LatestPublishedVersion.Public_URL__c from ContentDocument $WHERE Id ="

        val TASK_LIST = "Select Id,Subject,ActivityDate,What.Name, What.Id from Task $WHERE $OWNER_FILTER"

//
//        var TASK_AND_OPPORTUNITY_DETAIL_TODAY = "Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task $WHERE $WHAT_ID_IN_TASK_FILTER $AND $RECORD_TYPE_ID_FILTER $AND $ACTIVITY_DATE_TODAY_FILTER"
//        var TASK_AND_OPPORTUNITY_DETAIL = "Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task $WHERE $WHAT_ID_IN_TASK_FILTER $AND $RECORD_TYPE_ID_FILTER"

        var TODAY_OPPORTUNITY = "SELECT id, Name, closedate, Project__r.Name, StageName, Status__c,  Account_Mobile_Number__c, Sales_Call_Attempt_Date__c,Active_New__c from Opportunity WHERE Active_New__c=true AND  Sales_Next_Action_Date__c = TODAY"
        var ALL_MANUAL_TASK = "Select Id,whatId,Subject,Activitydate,What.Name,ProjectInterestedWeb__r.Name,createdDate,Task_Type__c from task where createdDate=Today"
    }
}