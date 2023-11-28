package com.stetig.solitaire.api
import com.salesforce.androidsdk.app.SalesforceSDKManager
import com.stetig.solitaire.utils.Utils

interface Query {
    companion object {
        private val USER_NAME: String = SalesforceSDKManager.getInstance().userAccountManager.currentUser.username
        private val USER_ID: String = "${SalesforceSDKManager.getInstance().userAccountManager.currentUser.userId}"
        const val SITE_VISIT = "Proposed site visit"
        const val FOLLOW_UP = "Follow up"
        const val FACE_TO_FACE = "Allotted site visit"

        const val NEED_ANALYSIS = "Qualification"
        const val NEGOTIATION = "Booking Confirmed"
        const val PROPOSAL = "Closed Lost"

        const val SCHEDULED_SITE_VISIT = "sales Manager Allocated"
        const val ONGOING_SITE_VISIT = "site visit initiated"
        const val PAST_SITE_VISIT = "Feedback submitted"

        const val BIP = "Token Amount Received - BIP"
        const val QUALIFICATION = "Qualification"

//        const val RECORD_TYPE_ID_FILTER = "RecordTypeId = '012C3000000Aqq9IAC'"//SB
        const val RECORD_TYPE_ID_FILTER = "RecordTypeId = '012C30000007DKOIA2'"//SB

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
        const val ACTIVITY_DATE_TODAY_FILTER = "Next_Action_Date__c = TODAY"
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

        val USER_ACCOUNT = "SELECT Id, firstName, LastName, Username, Phone, MobilePhone, Email, SM_Status__c from User $WHERE $USER_NAME_FILTER"
//      "SELECT COUNT(ID) FROM TASK WHERE RecordTypeId = 00DC30000006cdh AND WhatId != null and Call_Proposed_Date_Of_Visit__c != null AND ActivityDate = TODAY"
        val TODAY_TASK_SITE_VISIT_COUNT = "SELECT COUNT(ID) FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND $OWNER_FILTER AND Call_Proposed_Date_Of_Visit__c = Today AND Next_Action_Date__c = TODAY"
        val TODAY_TASK_FOLLOW_UP_COUNT = "SELECT COUNT(ID) FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND Call_Attempt_Status__c = 'Follow Up' AND $OWNER_FILTER AND Next_Action_Date__c = TODAY"
        val TODAY_TASK_FACE_TO_FACE_COUNT = "SELECT COUNT(ID) FROM site_visit__c WHERE Site_Visit_Stage__c ='Sales Manager Allocated' AND $OWNER_FILTER AND CreatedDate=Today AND Customer_Name__c != null"

//        SELECT COUNT(ID) FROM TASK WHERE RecordTypeId = '012C3000000Aqq9IAC' AND WhatId != null AND Call_Proposed_Date_Of_Visit__c != null
        val ALL_TASK_SITE_VISIT_COUNT = "SELECT COUNT(ID) FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND Call_Proposed_Date_Of_Visit__c >= Today AND $OWNER_FILTER"
        val ALL_TASK_FOLLOW_UP_COUNT = "SELECT COUNT(ID) FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND Call_Attempt_Status__c = 'Follow Up' AND $OWNER_FILTER"
        val ALL_TASK_FACE_TO_FACE_COUNT = "SELECT COUNT(ID) FROM site_visit__c WHERE  Type_of_Enquiry__c != null AND Site_Visit_Stage__c = 'Sales Manager Allocated' AND Customer_Name__c != null AND $OWNER_FILTER"

        val OPEN_OPPORTUNITY_LIST =  "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, CreatedDate, Account_Email__c, Sales_Call_Attempt_Date__c, Project__r.Name from Opportunity $WHERE $OWNER_FILTER AND (StageName =  'Qualification' OR StageName =  'Qualified'OR StageName =  'Proposal Shared')"
        val BOOKED_OPPORTUNITY_LIST =  "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, CreatedDate, Account_Email__c, Sales_Call_Attempt_Date__c, Project__r.Name from Opportunity $WHERE $OWNER_FILTER AND (StageName =  'Booking Confirmed' OR StageName='Token Amount Received')"

        val OPPORTUNITY_LIST = "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, CreatedDate, Account_Email__c, Sales_Call_Attempt_Date__c, Project__r.Name from Opportunity $WHERE $OWNER_FILTER $AND $STAGE_NAME_FILTER"
        val OPPORTUNITY_DETAIL = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, Account_Email__c, CreatedDate, Sales_Call_Description__c, CloseDate,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Configuration__c from Opportunity $WHERE id="
        val ALL_ACTIVE_OPPORTUNITY = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, Account_Email__c, CreatedDate, Sales_Call_Description__c, CloseDate,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Configuration__c from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND Active_New__c = true $ASC"
        val ALL_ACTIVE_OPPORTUNITY_ORDER_BY = "SELECT Name, Id, StageName,Sales_Call_Attempt_Status__c, Status__c, CreatedDate, Sales_Call_Description__c,Account_Email__c ,CloseDate, Walk_in_Source__c, LeadSource, Configuration__c,Project_Type__c,Booking__r.Name, Project__c,Account_Mobile_Number__c, Project__r.Name from Opportunity WHERE Active_New__c = true AND $OWNER_FILTER ORDER BY CreatedDate DESC"
        val EXPIRING_OPPORTUNITY_IN_7_DAYS = "SELECT id, Name, CloseDate, Project__r.Name, StageName, Status__c,  Account_Mobile_Number__c, Sales_Call_Attempt_Date__c from Opportunity $WHERE $OWNER_FILTER $AND $STAGE_NAME_NOT_EQUAL_LOST_FILTER $AND ( $CLOSE_DATE_FILTER $OR $CLOSE_DATE_TODAY_FILTER)"

        val SITE_VISIT_LIST = "SELECT Id,Name,Customer_Name1__c,Type_of_Visit__c, Customer_Name__c, SM_feedback_on_customer_Remarks__c, Type_of_Enquiry__c,Opportunity_Project__c,Opportunity_Sourcing_Manager__c,createdDate,Site_Visit_Stage__c from site_visit__c $WHERE $OWNER_FILTER $AND $SITE_VISIT_FILTER"

        val SITE_VISIT_DETAIL = "SELECT Property_Type__c,Property_SubType__c,Purpose_of_Purchase__c,Zone__c,Desired_Possession__c,Budget__c from site_visit__c $WHERE id="
        var PROJECT_NAME_FROM_OPPORTUNITY_NUMBER = "SELECT Account_Email_Id__c, Project__c, Account_Mobile_Number__c, Project__r.Name from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND Account_Mobile_Number__c = "

        val PROJECTS_COLLATERAL = "Select Id, RDS_City__c,RDS_State__c,Address__c ,Name FROM Project__c"
        val PROJECTS_COLLATERAL_CITY_FILTER = "SELECT Id ,Name,RDS_City__c,RDS_State__c,Address__c FROM Project__c $WHERE $CITY_FILTER"
        val PROJECT_DETAIL_COLLATERAL = "SELECT Id, Name, Project__c, Collateral_Link__c, Project__r.Name FROM Project_Collateral__c  $WHERE $PROJECT_COLLATERAL_ACTIVE $AND $PROJECT_NAME_FILTER"

        val APPROVAL_LIST_COUNT = "SELECT COUNT(ID) FROM Cost_Sheet__c $WHERE Status__c ="
//      val APPROVAL_LIST = "SELECT Id,Opportunity_Name__c,Name, Status__c, Project__r.Name, Tower__r.Name, Project_Unit__r.Name, Opportunity_Name__r.Name, Opportunity_Name__r.Owner.Name FROM Cost_Sheet__c $WHERE User__c = ${Utils.buildQueryParameter(USER_ID)} $AND Status__c ="
        val APPROVAL_LIST = "Select id,Name,Owner.Name,Project__r.Name ,Project_Unit__r.name,Project_Unit__r.TowerName__r.Name, (Select id, Status__c,Tower__r.Name, Opportunity_Name__c, Opportunity_Name__r.Name from Cost_Sheets1__r where User__c = '$USER_ID' and Status__c = 'Approval Pending'),(Select id,Customer__c, Name, Status__c,Customer__r.Name from Payment_Plans__r where Approval_Person__c = '$USER_ID' and Status__c = 'Approval Pending') from Opportunity"
        val APPROVAL_TABLE_FIELDS = "Select Id,Base_Rate__c,Base_Rate_Original__c,Total_Amount_for_Unit__c,Infrastructure_Charges__c,Infrastructure_Charges_Original__c,e_IBE_MSEB_Development_Charges__c,IBE_MSEB_Development_Charges_Original__c,Premium_Charges__c,Premium_Charges_Original__c,Floor_Rise__c,Floor_Rise_Original__c,Legal_Charges__c,Legal_Charges_Original__c,X1_Total_Consideration_Value_c_d_e__c,Total_Consideration_Value_Original__c,Total_Consideration_Value_Difference__c,Stamp_Duty_Waived_Off__c,GST_Waived_Off__c,Registration_Charges_Waived_Off__c from Cost_Sheet__c $WHERE Opportunity_Name__c = "

        val CAMPAIGN_APPROVAL_LIST_COUNT = "SELECT COUNT(ID) FROM Campaign $WHERE $RECORD_TYPE_ID_FILTER $AND Approval_Status__c="
        val CAMPAIGN_APPROVAL_LIST = "Select Id, Name, Primary_Project__r.Name,Parent.Name,Approval_Status__c from Campaign $WHERE $RECORD_TYPE_ID_FILTER $AND ((Approval_Status__c='Submitted for Approval' $AND Level_1_Approver__c=${Utils.buildQueryParameter(USER_ID)}) $OR (Approval_Status__c='Level 1 Approved' $AND Level_2_Approver__c!=null $AND Level_2_Approver__c=${Utils.buildQueryParameter(USER_ID)}))"
        val CAMPAIGN_TABLE_FIELDS = "Select Id, Name, Primary_Project__r.Name,Parent.Name,BudgetedCost, Approval_Status__c , StartDate,EndDate from Campaign $WHERE $RECORD_TYPE_ID_FILTER $AND  id = "

        val SOURCE_CHANGE_APPROVAL_LIST = "Select Id,Name,Customer_Name__c,Customer_Name__r.Name,Owner.Name,Visit_source_approval_Status__c from site_visit__c WHERE Visit_source_approval_Status__c = 'Approval Initiated' AND VisitSourceApprover__r.username = ${Utils.buildQueryParameter(USER_NAME)}"
        val SOURCE_CHANGE_TABLE_FIELDS = "SELECT Id, Site_Visit_Stage__c, Type_of_Enquiry__c, Customer_Name__c, Project__r.Name, Mobile_No2__c, Source1__c, Opportunity_Walk_in_Source__c, Opportunity_Walk_in_Sub_Source__c, Sub_Source2__c,Channel_Partner_Leasing__c FROM site_visit__c $WHERE Visit_source_approval_Status__c='Approval Initiated' $AND id = "

        val CP_CREATION_APPROVAL_LIST = "Select Id,NTS_ID__c,Name,CP_Sub_Type__c,Firm_Name__c,Sourcing_Manager__r.Name,CP_Type__c,Maha_RERA_No__c,RERA_Expiry_Date__c,Zone__c,Expertise__c,Location_Of_the_CPs_Office__c,Level_1_Submitted_Date_Time__c,Level_2_Date_Time__c from NTS_CP__c where ((Level_1_Approval_Status__c!='Approved') OR (Level_2_Approval_Status__c!='=Approved')) AND NTS_CP_Status__c!='Approved' $AND $OWNER_FILTER"
        val CP_CREATTION_TABLE_FIELDS = "Select id, NTS_ID__c, Name, CP_Sub_Type__c, Firm_Name__c, Sourcing_Manager__r.Name, CP_Type__c, Maha_RERA_No__c, RERA_Expiry_Date__c, Zone__c, Expertise__c, Location_Of_the_CPs_Office__c, Level_1_Submitted_Date_Time__c, Level_2_Date_Time__c from NTS_CP__c where ((Level_1_Approval_Status__c!='Approved' AND Sourcing_Manager__c='$USER_ID' ) OR (Level_2_Approval_Status__c!='Approved' AND Sourcing_TL__c='$USER_ID' )) AND NTS_CP_Status__c!='Approved'"

        val SEARCH = "SELECT Name, Id, StageName, Status__c, Account_Mobile_Number__c, Sales_Call_Attempt_Date__c, Project__c, Project__r.Name from Opportunity $WHERE $OPPORTUNITY_ACTIVE_FILTER $AND $OWNER_FILTER $AND"
        val TIME_LINE_QUERY = "Select Id, Mobile_Number_Webform__c, Task_Type__c, Subject, ActivityDate, createddate, WhatId, status, RecordTypeId FROM Task $WHERE "
//        Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task WHERE whatid in (select id from opportunity) AND RecordTypeId = '012C3000000Aqq9IAC'

        val PROPOSED_SITE_VISIT_LIST_TODAY = "SELECT ID, what.Name, what.Id, Status, CreatedDate, Call_Proposed_Date_Of_Visit__c, Description, Subject  FROM TASK $WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') $AND Call_Proposed_Date_Of_Visit__c = Today $AND $ACTIVITY_DATE_TODAY_FILTER AND $OWNER_FILTER"
        val PROPOSED_SITE_VISIT_LIST = "SELECT ID, what.Name, what.Id,CreatedDate,Status, Call_Proposed_Date_Of_Visit__c, Description, Subject FROM TASK $WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') $AND  Call_Proposed_Date_Of_Visit__c >= Today $AND $OWNER_FILTER"

        val FOLLOW_UP_TODAY =  "SELECT ID,what.Name, what.Id,CreatedDate, Status, Subject, Next_Action_Date__c , ProjectInterestedWeb__c,Description FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND Call_Attempt_Status__c = 'Follow up' AND $OWNER_FILTER AND Next_Action_Date__c = TODAY"
        val FOLLOW_UP_LIST = "SELECT ID,what.Name, what.Id,CreatedDate, Status, Subject, Next_Action_Date__c ,ProjectInterestedWeb__c,Description FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND Call_Attempt_Status__c = 'Follow up'  AND $OWNER_FILTER "

        val ALOTED_SITE_VISIT_LIST_TODAY = "SELECT ID,Site_Visit_Stage__c, CreatedDate,  Customer_Name__r.Name, Type_of_Enquiry__c,Customer_Name__c,Project__r.Name,Mobile_No2__c  FROM site_visit__c $WHERE Site_Visit_Stage__c = 'Sales Manager Allocated' $AND CreatedDate=Today AND $OWNER_FILTER"
        val ALOTED_SITE_VISIT_LIST = "SELECT ID, Site_Visit_Stage__c, CreatedDate,  Customer_Name__r.Name, Type_of_Enquiry__c,Customer_Name__c,Project__r.Name,Mobile_No2__c  FROM site_visit__c WHERE Type_of_Enquiry__c != null AND Site_Visit_Stage__c = 'Sales Manager Allocated' AND Customer_Name__c != null AND $OWNER_FILTER"

        val PROJECT_LINK_FETCH = "SELECT Id, ContentDocumentId FROM ContentDocumentLink $WHERE linkedEntityId ="
        val PROJECT_LINK = "SELECT Id, LatestPublishedVersion.Title,LatestPublishedVersion.Public_URL__c from ContentDocument $WHERE Id ="

        val TASK_LIST = "Select Id,Subject,ActivityDate,What.Name, What.Id from Task $WHERE $OWNER_FILTER"

//
//        var TASK_AND_OPPORTUNITY_DETAIL_TODAY = "Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task $WHERE $WHAT_ID_IN_TASK_FILTER $AND $RECORD_TYPE_ID_FILTER $AND $ACTIVITY_DATE_TODAY_FILTER"
//        var TASK_AND_OPPORTUNITY_DETAIL = "Select Id, ActivityDate, WhatId, What.Name , CreatedDate FROM Task $WHERE $WHAT_ID_IN_TASK_FILTER $AND $RECORD_TYPE_ID_FILTER"

        var TODAY_OPPORTUNITY = "SELECT id, Name, closedate, Project__r.Name, StageName, Status__c,  Account_Mobile_Number__c, Sales_Call_Attempt_Date__c,Active_New__c from Opportunity WHERE Sales_Next_Action_Date__c = TODAY AND $OWNER_FILTER"
        var ALL_MANUAL_TASK = "SELECT ID,what.Name,Call_Proposed_Date_Of_Visit__c, what.Id,CreatedDate, Status, Subject, Next_Action_Date__c ,ProjectInterestedWeb__c,Description FROM TASK WHERE (RecordTypeId = '012C3000000Aqq9IAC' OR RecordTypeId = '012C3000000ArizIAC') AND (Call_Attempt_Status__c = 'Follow up'  OR Call_Proposed_Date_Of_Visit__c !=null) AND (Next_Action_Date__c = Today  OR Call_Proposed_Date_Of_Visit__c >=Today)  AND  $OWNER_FILTER ORDER BY CreatedDate DESC"

        var CCR_APPROVAL_LIST = "Select Id,Name,Status__c,Updated_Budgeted_Cost_in_Campaign__c,Updated_Campaign_Start_Date__c,Updated_Campaign_End_Date__c from Campaign_Change_Request__c where (Status__c='Submitted for Approval' AND Level_1_Approver__c= ${Utils.buildQueryParameter(USER_ID)}) OR (Status__c= ${Utils.buildQueryParameter(USER_ID)} AND Level_2_Approver__c = ${Utils.buildQueryParameter(USER_ID)})"
        var CCR_APPROVAL_LIST_DETAIL = "Select Id,Name,Status__c,Updated_Budgeted_Cost_in_Campaign__c,Updated_Campaign_Start_Date__c,Updated_Campaign_End_Date__c from Campaign_Change_Request__c Where Id = "

        var OPPORTUNITY_ON_MOBILE_NUMBER = "SELECT Id, Name,Account_Mobile_Number__c,OwnerId,Project__r.Name FROM Opportunity WHERE StageName IN ('Proposal Shared','Qualification','Site Visit Done','Qualified') AND OwnerId = ${Utils.buildQueryParameter(USER_ID)} AND Account_Mobile_Number__c = "
    }
}