/* 
	WebCenter Analytics - Content Presenter Metrics -
	Author: Daniel Merch�n Garc�a / VASSIT Services LTD 
	Version: 1.0
	
	Tables:
            DIMENSION TABLE: ASDIM_WC_PRESENTER_0 ({HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CONTENTPRESENTER)
            EVENT TABLES: ASFACT_WC_PRESENTER_0 (HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CPDETAILVIEW)
                          ASFACT_WC_PRESENTER_1 (HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CPLISTVIEW)
*/

/* 
	ASDIM_WC_PRESENTER_0 to register content shown in Content Presenter Task Flow --
	This table contains following information about the content:
		- RESOURCEID_: [RepositoryName]#dDocName:[dDocName] 
		- NAME_: [dDocTitle]
		- CONTENTID: [dDocName]
		- DESCRIPTION: [xComments]
		- REPOSITORY: [UCM Repository]
		- CONTENTTYPE: Type of the content (WCM:RD, IDC:GlobalProfile...)
		- RESOURCETYPE: Type of the resource (blog, wiki, webContent, document, folder)
		- FILENAME_: UCM FileName
		- PATH_: [UCM Path]
		- SMALLICON: ICON16 of the content
		- LARGEICON: ICON32 of the content
*/

/*
    Installation:
        - Execute the script in ACTIVITIES WebCenter Schema
*/
CREATE TABLE ASDIM_WC_PRESENTER_0
( ID            NUMBER(38,0)   NOT NULL ENABLE
, CREATED       DATE           NOT NULL ENABLE
, LASTMODIFIED  DATE           NOT NULL ENABLE
, RESOURCEID_   NVARCHAR2(254) NOT NULL ENABLE
, NAME_         NVARCHAR2(254)
, CONTENTID_    NVARCHAR2(254)
, DESCRIPTION_  NVARCHAR2(254)
, REPOSITORY_   NVARCHAR2(254)
, CONTENTTYPE_   NVARCHAR2(254)
, RESOURCETYPE_ NVARCHAR2(254)
, FILENAME_     NVARCHAR2(254)
, PATH_         NVARCHAR2(254)
, SMALLICON_    NVARCHAR2(254)
, LARGEICON_    NVARCHAR2(254)
, CONSTRAINT ASDIM_WC_PRESENTER_0 PRIMARY KEY (ID) ENABLE
);
-- Index for OTS searching --  
CREATE UNIQUE INDEX IDX_ASDIM_WC_PRESENTER_0 ON ASDIM_WC_PRESENTER_0 ( "RESOURCEID_" );

-- Create of tables that will store the number of occurrences of each event --
-- ASFACT_WC_PRESENTER_0 will register the amount of detail content visits -- 
CREATE TABLE ASFACT_WC_PRESENTER_0
( ID           NUMBER(38,0) NOT NULL ENABLE
, OCCURRED     DATE         NOT NULL ENABLE
, TIMEID       NUMBER(38,0) NOT NULL ENABLE
, USERID       NUMBER(38,0)
, EVENTID      NUMBER(38,0)
, CONTENT_     NUMBER(38,0)
, APPLICATION_ NUMBER(38,0)
, USER_AGENT_  NUMBER(38,0)
, CLIENT_IP_   NUMBER(38,0)
, GROUPSPACE_  NUMBER(38,0)
, SESSION_ID_  NVARCHAR2(254)
, CONSTRAINT PK_ASFACT_WC_PRESENTER_0 PRIMARY KEY (TIMEID, ID) ENABLE
);

-- ASFACT_WC_PRESENTER_1 will register the amount of list content visits -- 
CREATE TABLE ASFACT_WC_PRESENTER_1
( ID           NUMBER(38,0) NOT NULL ENABLE
, OCCURRED     DATE         NOT NULL ENABLE
, TIMEID       NUMBER(38,0) NOT NULL ENABLE
, USERID       NUMBER(38,0)
, EVENTID      NUMBER(38,0)
, CONTENT_     NUMBER(38,0)
, APPLICATION_ NUMBER(38,0)
, USER_AGENT_  NUMBER(38,0)
, CLIENT_IP_   NUMBER(38,0)
, GROUPSPACE_  NUMBER(38,0)
, SESSION_ID_  NVARCHAR2(254)
, CONSTRAINT PK_ASFACT_WC_PRESENTER_1 PRIMARY KEY (TIMEID, ID) ENABLE
);

-- Including the new events to the Analytics Events --
INSERT INTO ASSYS_EVENTS
( ID
, CREATED
, LASTMODIFIED
, DISPLAYNAME
, DESCRIPTION
, TABLENAME
, ISCUSTOM
, ISACTIVE
, ISPERSISTENT
, ISACTION
)
VALUES
( S_ANALYTICS_SEQUENCE.NEXTVAL
, SYSDATE
, SYSDATE
, '{HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CPDETAILVIEW'
, 'Custom event for registering content that appears in detail Content Presenter Template'
, 'ASFACT_WC_PRESENTER_0'
, 1
, 1
, 1
, 0);
INSERT INTO ASSYS_EVENTS
( ID
, CREATED
, LASTMODIFIED
, DISPLAYNAME
, DESCRIPTION
, TABLENAME
, ISCUSTOM
, ISACTIVE
, ISPERSISTENT
, ISACTION
)
VALUES
( S_ANALYTICS_SEQUENCE.NEXTVAL
, SYSDATE
, SYSDATE
, '{HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CPLISTVIEW'
, 'Custom event for registering content that appears in list Content Presenter Template'
, 'ASFACT_WC_PRESENTER_1'
, 1
, 1
, 1
, 0);

-- Adding the dimensions to Analytics DIM --
INSERT INTO ASSYS_EVENTDIMENSIONS
( ID
, CREATED
, LASTMODIFIED
, DISPLAYNAME
, TABLENAME
, ISUNIQUE
, IDENTIFYINGUSER
)
VALUES
( S_ANALYTICS_SEQUENCE.NEXTVAL
, SYSDATE
, SYSDATE
, '{HTTP://WWW.ORACLE.COM/ANALYTICS/WC}CONTENTPRESENTER'
, 'ASDIM_WC_PRESENTER_0'
, 0
, 0
);

/*
	Registering Event Mapping for the information related to an occurrence of detail event
*/
INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'CONTENT', 'CONTENT_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_0' and dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'APPLICATION', 'APPLICATION_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_0' and dim.tablename='ASDIM_WC_APPLICA_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'USER_AGENT', 'USER_AGENT_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_0' and dim.tablename='ASDIM_WC_USER_AG_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'CLIENT_IP', 'CLIENT_IP_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_0' and dim.tablename='ASDIM_WC_CLIENT__0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'GROUPSPACE', 'GROUPSPACE_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_0' and dim.tablename='ASDIM_WC_GROUPSP_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'SESSION_ID', 'SESSION_ID_', 2, 254, NULL
from ASSYS_EVENTS event
where event.tablename= 'ASFACT_WC_PRESENTER_0';

/*
	Registering Event Mapping for the information related to an occurrence of list event
*/
INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'CONTENT', 'CONTENT_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_1' and dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'APPLICATION', 'APPLICATION_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_1' and dim.tablename='ASDIM_WC_APPLICA_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'USER_AGENT', 'USER_AGENT_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_1' and dim.tablename='ASDIM_WC_USER_AG_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'CLIENT_IP', 'CLIENT_IP_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_1' and dim.tablename='ASDIM_WC_CLIENT__0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'GROUPSPACE', 'GROUPSPACE_', -1, -1, dim.id 
from ASSYS_EVENTS event, ASSYS_EVENTDIMENSIONS dim 
where event.tablename= 'ASFACT_WC_PRESENTER_1' and dim.tablename='ASDIM_WC_GROUPSP_0';

INSERT INTO ASSYS_EVENTFACTS
( ID
, CREATED
, LASTMODIFIED
, EVENTID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPEID
, COLUMNLENGTH
, EVENTDIMENSIONID
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, event.id, 'SESSION_ID', 'SESSION_ID_', 2, 254, NULL
from ASSYS_EVENTS event
where event.tablename= 'ASFACT_WC_PRESENTER_1';

/*
	DIMENSION EVENT MAPPING TO FILL ALL CUSTOM INFORMATION OF THE DIMENSION
*/
INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'NAME', 'NAME_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'RESOURCEID', 'RESOURCEID_', 2, 254, 1
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'CONTENTID', 'CONTENTID_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'DESCRIPTION', 'DESCRIPTION_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'REPOSITORY', 'REPOSITORY_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'CONTENTTYPE', 'CONTENTTYPE_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'RESOURCETYPE', 'RESOURCETYPE_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'FILENAME', 'FILENAME_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'PATH', 'PATH_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'SMALLICON', 'SMALLICON_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';

INSERT INTO ASSYS_DIMENSIONPROPS
( ID
, CREATED
, LASTMODIFIED
, DIMENSIONID
, DISPLAYNAME
, COLUMNNAME
, COLUMNTYPE
, COLUMNLENGTH
, ISKEY
)
SELECT S_ANALYTICS_SEQUENCE.NEXTVAL, SYSDATE, SYSDATE, dim.id, 'LARGEICON', 'LARGEICON_', 2, 254, 0
from ASSYS_EVENTDIMENSIONS dim 
where dim.tablename='ASDIM_WC_PRESENTER_0';
