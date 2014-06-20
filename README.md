WebCenter Portal Samples / Extensions / Components
================

Project: Analytics_Content_Presenter
-------------
Extension to collect analytics when a Content is viewed in a Detail or List Content Presenter Template
- AnalyticsExtensions: Project containing the WebCenter Analytics Content Presenter API Wrapper and the SQL to create the required tables and the inserts to register the new Collector Events in WebCenter Analytics Schema.
- OWCPortalExtensions: WebCenter Portal Server Extension project to reference the AnalyticsExtensions Shared-lib in WebCenter Portal.
- OWCTFCustomizations: Contains the required customizations to be done in Content Presenter to add the Analytics Extension Code

===========================
Project: Twitter_Search_Live_Adapter
------------- 
WebCenter Live Search Adapter to integrate Twitter as new search resource.
- TwitterSearchLiveAdapter: Contains the code to generate the ADF JAR Library to be deployed in WebCenter Portal or WebCenter Framework Portal

===========================
Project: LikesCommentsExtension
------------- 
Bean that can be used to retrieve the information required by <likes:likesLink> to be used in Content Presenter.
In addition allows to see, comment and use the Comments functionality with oracle.webcenter.content resources

Sample:
<likes:likesLink id="ol1" rendered="#{true}"
                             serviceId="#{node.propertyMap['webcenter:serviceid'].value.stringValue}"
                             objectType="#{node.propertyMap['webcenter:resourcetype'].value.stringValue}"
                             objectId="#{backingBeanScope.likesComments.nodeLikesComments[node].activityId}"
                             scopeId="#{backingBeanScope.likesComments.nodeLikesComments[node].scopeGUID}"
                             likesCount="#{backingBeanScope.likesComments.nodeLikesComments[node].likesCount}"
                             myLike="#{backingBeanScope.likesComments.nodeLikesComments[node].myLike}"/>
							 
- LikesComments: Project that contains the Java Classes that uses WebCenter Activity Streaming API to retrieve Likes.
- SharedLib: Project to deploy Likes and Comments extension as Shared-lib and be used by WebCenter Portal or WebCenter Framework Portal.

This version just includes Likes. Comments will be included soon.




 
