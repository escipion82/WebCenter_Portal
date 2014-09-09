WebCenter Portal Samples / Extensions / Components
================

Project: Analytics_Content_Presenter
-------------
Extension to collect analytics when a Content is viewed in a Detail or List Content Presenter Template
- AnalyticsExtensions: Project containing the WebCenter Analytics Content Presenter API Wrapper and the SQL to create the required tables and the inserts to register the new Collector Events in WebCenter Analytics Schema.
- OWCPortalExtensions: WebCenter Portal Server Extension project to reference the AnalyticsExtensions Shared-lib in WebCenter Portal.
- OWCTFCustomizations: Contains the required customizations to be done in Content Presenter to add the Analytics Extension Code

Documentation:
- http://www.vassit.co.uk/assets/VASSIT-WebCenter-Analytics-Extension-for-Content-Presenter.pdf

===========================
Project: Twitter_Search_Live_Adapter
------------- 
WebCenter Live Search Adapter to integrate Twitter as new search resource.
- TwitterSearchLiveAdapter: Contains the code to generate the ADF JAR Library to be deployed in WebCenter Portal or WebCenter Framework Portal

Documentation: 
- http://www.oracle.com/technetwork/articles/enterprise2/garcia-wcp-search-adaptor-2066866.html

===========================
Project: LikesCommentsExtension
------------- 
Bean that can be used to retrieve the information required by <likes:likesLink> to be used in Content Presenter.
In addition allows to see, comment and use the Comments functionality with oracle.webcenter.content resources
							 
- LikesComments: Project that contains the Java Classes that uses WebCenter Activity Streaming API to retrieve Likes.
- SharedLib: Project to deploy Likes and Comments extension as Shared-lib and be used by WebCenter Portal or WebCenter Framework Portal.

This version just includes Likes. Comments will be included soon.

Documentation: 
- Spanish: http://danielmerchanoracle.blogspot.co.uk/2014/04/integrando-webcenter-likes-comments-api.html
- English: http://www.vassit.co.uk/knowledge/integrating-webcenter-likes-comments-api-i/

===========================
Project: PortalTranslationSample
------------- 
WebCenter Framework Portal 11.1.1.8 implementing a Multilanguage solution for literals.

It is based on a Java Filter for User Preference Locale and an ADF Phase Listener to refresh and invalidate the Navigation Model Cache in case of using Resource Bundle in the Title Attribute of the Navigation Resources.
							 
Documentation: 
- Spanish: http://danielmerchanoracle.blogspot.co.uk/2014/09/multilenguaje-en-una-portal-framework.html
- English: http://www.vassit.co.uk/knowledge/multilanguage-in-webcenter-framework-portal/



 
