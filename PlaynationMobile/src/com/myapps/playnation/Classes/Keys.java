package com.myapps.playnation.Classes;

public final class Keys {
	public static final String UserName = "UserName";
	public static final String Password = "Password";
	public static final String ActiveSession = "ActiveSession";

	// ID is used by the DataConnector class to know which table to search for
	// same principle
	// as table name but needed int for Switch { (case): }
	public static final int gamesID = 1;
	// nameTable is the key for the HashMap where the <name> resides
	public static final String gamesTable = "game";
	// The name of the Keys for the HashMap List ( coincides with the names of
	// the Database columnNames )
	public final static String Name = "GameName";
	public final static String ID_GAME = "ID_GAME";
	public static final String GAMENAME = "GameName";
	public static final String GAMETYPE = "GameType";
	public static final String GAMEDESC = "GameDesc";
	public static final String RATING = "SocialRating";
	public static final String GAMEDATE = "CreationDate";
	public static final String GAMEESRB = "ESRB";
	public static final String GAMEURL = "URL";
	public static final String GAMEPLAYERSCOUNT = "PlayersCount";
	public static final String GAMEPLATFORM = "PlatformName";
	public static final String GAMETYPENAME = "GameTypeName";
	public static final String GAMEPROCINFO = "GameInfo";
	public static final String GAMEPROCCOMPANY = "GameCompany";
	public static final String GAMEisDeveloper = "isDeveloper";
	public static final String GAMEisDistributor = "isDistributor";
	public static final String GAMECompanyDeveloper = "Developer";
	public static final String GAMECompanyDistributor = "Distributor";
	public static final String GAMELILDBTABLENAME = "GAMEINFOTABLE";

	public static final int commentsID = 2;

	public static final String commentsTable = "Comments";
	public static final String USERNAME = "Name";
	public static final String COMMENT = "Comment";
	public static final String ID_OWNER = "ID_OWNER";
	public static final String OWNERTYPE = "OwnerType";
	public static final String CommentTime = "PostingTime";

	public static final int replysID = 3;
	public static final String replysTable = "Replys";

	public static final int groupsID = 4;
	public static String groupsTable = "groups";
	public final static String ID_GROUP = "ID_GROUP";
	public static final String GROUPNAME = "GroupName";
	public static final String GROUPDESC = "GroupDesc";
	public static final String GROUPTYPE = "GroupType1";
	public static final String GROUPTYPE2 = "GroupType2";
	public static final String GROUPDATE = "CreatedTime";

	public static final int playersID = 5;

	public static String playersTable = "player";
	public static final String PLAYERNAME = "PlayerName";
	public static final String PLAYERDESC = "PlayerDesc";
	public static final String PLAYERTYPE = "PlayerType1";
	public static final String PLAYERTYPE2 = "PlayerType2";
	public static final String PLAYERDATE = "CreatedTime";

	public static final int newsID = 6;

	// Only for storing in lilDB temp instance of newsTab
	public static String newsTempTable = "TempNews";
	public static String companyTempTable = "TempCompany";

	public static String newsTable = "news";
	public static final String NEWSNAME = "NewsName";
	public static final String NEWSDESC = "NewsDesc";
	public static final String NEWSTYPE = "NewsType1";
	public static final String NEWSTYPE2 = "NewsType2";
	public static final String NEWSDATE = "CreatedTime";

	// in the future hopefully for image
	public static final String ICON = "icon";

	// State used for the mainList mostly to keep track of the current selected
	// state/tab
	public static final int HomeSTATE = 0;
	public static final int GamesSTATE = 1;
	public static final int GroupsSTATE = 2;
	public static final int NewsSTATE = 3;
	public static final int PlayersSTATE = 4;
	public static final int CompaniesSTATE = 5;

	public static final String ARG_POSITION = "int";
	// EXTRA

	/*
	 * Tab control
	 */
	// Games+Groups
	public static final int GAMES_TAB_COUNT = 5;
	public static final String TAB_INFO = "Info";
	public static final String TAB_NEWS = "News";
	public static final String TAB_WALL = "Wall";
	public static final String TAB_REVIEW = "Reviews";
	public static final String TAB_PLAYERS = "Players";

	public static final String TAB_FORUM = "Forum";
	public static final String TAB_MEDIA = "Media";

	// Home+Players
	public static final int HOME_TAB_COUNT = 6;
	public static final String TAB_MESSAGES = "Messages";
	public static final String TAB_GROUPS = "Groups";
	public static final String TAB_GAMES = "Games";
	public static final String TAB_SUBSCRIPTION = "Subs";
	public static final String TAB_FRIENDS = "Friends";
	public static final String TAB_EVENTS = "Events";

	public static final String TAB_EDITPROFILE = "Profile";

	/*
	 * Fragment Tags (not sure if necessary)
	 */
	public static final String GAME_INFO_FRAG = "gameInfo";
	public static final String GAME_LIST_FRAG = "gameHead";
	public static final String GROUP_LIST_FRAG = "groupHead";

	/*
	 * Extra Strings for tests
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final int Padding = 12;

	/*
	 * Post variables for php script
	 */
	public final static String POSTID_GAMEW = "id_gameW";
	public final static String POSTID_PLAYER = "id";
	public final static String POSTWallItem = "wallitem";
	public final static String POSTTableName = "tableName";
	public final static String POSTorderByColumn = "orderbyColumn";

	/*
	 * NewsFeed json column identifiers. Also keys for map
	 */
	public final static String NEWSCOLID_NEWS = "ID_NEWS";
	public final static String NEWSCOLNEWSTEXT = "NewsText";
	public final static String NEWSCOLINTROTEXT = "IntroText";
	public final static String NEWSCOLHEADLINE = "Headline";
	public final static String NEWSCOLPOSTINGTIME = "PostingTime";
	// TODO ADD IMAGE REFERENCE
	public final static String NEWSCOLIMAGE = "IMAGE";

	/*
	 * Who is Playing json and map identifiers
	 */

	public final static String TEMPLAYERID = "12";
	public final static String ID_PLAYER = "ID_PLAYER";
	public final static String PLAYERNICKNAME = "DisplayName";
	public final static String PLAYERAVATAR = "Avatar";
	public final static String FirstName = "FirstName";
	public final static String LastName = "LastName";
	public final static String CITY = "City";
	public final static String COUNTRY = "Country";
	public final static String Email = "EMail";
	public final static String Age = "DateOfBirth";
	public final static String Author = "Author";

	// Notification json and map identifiers
	public final static String ID_NOTIFICATION = "ID_NOTIFICATION";
	public final static String NotificationType = "NotificationType";
	public final static String NotificationTime = "NotificationTime";
	public final static String NotificationFromType = "FromType";
	public final static String NotificationID_FROM = "ID_FROM";
	public final static String NotificationLastReadTime = "LastReadTime";
	public final static String NotificationisRead = "isRead";

	// Messages json and map identifiers
	public final static String ID_MESSAGE = "ID_MESSAGE";
	public final static String MessageID_CONVERSATION = "ID_CONVERSATION";
	public final static String MessageText = "MessageText";
	public final static String MessageTime = "MessageTime";

	// Events json and map identifiers
	public final static String ID_EVENT = "ID_EVENT";
	public final static String EventID_COMPANY = "ID_COMPANY";
	public final static String EventID_TEAM = "ID_TEAM";
	public final static String EventIMAGEURL = "ImageURL";
	public final static String EventDescription = "EventDescription";
	public final static String EventDuration = "EventDuration";
	public final static String EventHeadline = "EventHeadline";
	public final static String EventLocation = "EventLocation";
	public final static String EventTime = "EventTime";
	public final static String EventType = "EventType";
	public final static String EventEndDate = "EventEndDate";
	public final static String EventIsPublic = "isPublic";
	public final static String EventInviteLevel = "InviteLevel";
	public final static String EventIsExpired = "isExpired";

	// Subscription json and map identifiers
	public final static String ID_ITEM = "ID_ITEM";
	public final static String ItemType = "ItemType";
	public final static String ItemName = "ItemName";
	public final static String SubscriptionTime = "SubscriptionTime";

	// Player Friends json and map identifiers
	public final static String ID_FRIEND = "ID_FRIEND";

	// Player Games json and map identifiers
	public final static String GamesisSubscribed = "isSubscribed";
	public final static String GamesSubscriptionTime = "SubscriptionTime";
	public final static String GameisPlaying = "isPlaying";
	public final static String GamePostCount = "PostCount";
	public final static String GameComments = "Comments";
	// Should be like that one exists above with lower case DB table
	public final static String GameID_GAMETYPE = "ID_GAMETYPE";

	// Player Group json and map identifiers
	public final static String isMember = "isMember";
	public final static String MemberTime = "MemberTime";
	public final static String hasApplied = "hasApplied";
	public final static String GroupMemberCount = "MemberCount";
	public final static String GruopCreatedTime = "CreatedTime";
	public final static String GruopIsLeader = "isLeader";
	public final static String GruopCreatorName = "CreatorName";

	// Player Wall json and map identifiers
	public final static String ID_WALLITEM = "ID_WALLITEM";
	public final static String ID_ORGOWNER = "ID_ORGOWNER";
	public final static String WallOwnerType = "OwnerType";
	public final static String WallPostingTime = "PostingTime";
	public final static String WallLastActivityTime = "LastActivityTime";
	public final static String WallMessage = "Message";
	public final static String WallPosterDisplayName = "PosterDisplayName";

	// Table Names
	public final static String HomeMsgRepliesTable = "PMsgReplies";
	public final static String HomeWallRepliesTable = "PWallReplies";
	public final static String HomeGroupTable = "PGroup";
	public final static String SearchGroupTable = "PSearchGroups";
	public final static String SearchFriendsTable = "PSearchFriends";
	public final static String SearchEventTable = "PSearchEvent";
	public final static String SearchGameTable = "PSearchGame";
	public final static String SearchSubscriptionTable = "PSearchSubs";
	public final static String HomeSubscriptionTable = "PSubscription";
	public final static String HomeMsgTable = "PMsg";
	public final static String PlayerTable = "Player";
	public final static String HomeGamesTable = "PGames";
	public final static String HomeNotificationTable = "PNotification";
	public final static String HomeWallTable = "PWall";
	public final static String HomeFriendsTable = "PFriends";
	public final static String HomeEventTable = "PEvent";
	public final static String companyTable = "Companies";

	// ArrayGameType

	public final static String CompanyName = "CompanyName";
	public final static String CompanyAddress = "CompanyAddress";
	public final static String CompanyFounded = "Founded";
	public final static String CompanyEmployees = "Employees";
	public final static String CompanyURL = "URL";
	public final static String CompanyDesc = "CompanyDesc";
	public final static String CompanyType = "CompanyType";
	public final static String CompanyCreatedTime = "CreatedTime";
	public final static String CompanyOwnership = "Ownership";
	public final static String CompanyImageURL = "ImageURL";
	public final static String CompanyNewsCount = "NewsCount";
	public final static String CompanyEventCount = "EventCount";
	public final static String CompanyGameCount = "GameCount";
	public final static String CompanySocialRating = "SocialRating";

	// Sorting names for company sub-menu results
	public final static String OrderByAlphabet = "Alphabet";
	public final static String OrderByPopularity = "Popularity";
	public final static String OrderByRating = "Rating";
	public final static String OrderByDateAdded = "Date";
	public final static String companysubNewsTAB = "Company";
	public final static String gamesubNewsTAB = "GAME";
	public static final String AppState = "ApplicationState";

	// Aplication States

}
