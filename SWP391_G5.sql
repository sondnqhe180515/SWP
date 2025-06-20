USE [SWP391_G5]
GO
/****** Object:  Table [dbo].[Account]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Account](
	[Account_Id] [int] IDENTITY(1,1) NOT NULL,
	[Account_Username] [nvarchar](50) NOT NULL,
	[Account_Password] [nvarchar](256) NOT NULL,
	[Account_Email] [nvarchar](255) NOT NULL,
	[Account_Fullname] [nvarchar](100) NULL,
	[Account_Phone] [nvarchar](20) NULL,
	[Account_Address] [nvarchar](255) NULL,
	[Account_Gender] [tinyint] NULL,
	[Account_Date] [date] NULL,
	[Account_Image] [nvarchar](max) NULL,
	[Role] [nvarchar](50) NULL,
	[Account_CreatedDate] [datetime] NULL,
	[Account_Status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Account_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Blog]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Blog](
	[Blog_Id] [int] IDENTITY(1,1) NOT NULL,
	[Blog_Title] [nvarchar](100) NULL,
	[Blog_Description] [nvarchar](max) NOT NULL,
	[Blog_Image] [nvarchar](255) NOT NULL,
	[Blog_Status] [nvarchar](50) NULL,
	[Blog_CreatedDate] [datetime] NULL,
	[Blog_CreatedBy] [int] NULL,
	[CategoryBlog_Id] [int] NULL,
	[Blog_Content] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[Blog_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category_Blog]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category_Blog](
	[CategoryBlog_Id] [int] IDENTITY(1,1) NOT NULL,
	[CategoryBlog_Name] [nvarchar](255) NOT NULL,
	[CategoryBlog_Status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[CategoryBlog_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category_drug]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category_drug](
	[categoryDrug_id] [int] IDENTITY(1,1) NOT NULL,
	[categoryDrug_name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[categoryDrug_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Certification]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Certification](
	[Certification_Id] [int] IDENTITY(1,1) NOT NULL,
	[Certification_Name] [nvarchar](100) NOT NULL,
	[Certification_CreatedBy] [int] NOT NULL,
	[Certification_Image] [nvarchar](255) NULL,
	[Certification_Status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Certification_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comment]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comment](
	[Comment_Id] [int] IDENTITY(1,1) NOT NULL,
	[Blog_Id] [int] NOT NULL,
	[Comment_Content] [nvarchar](max) NULL,
	[Comment_Status] [nvarchar](50) NULL,
	[Comment_CreatedDate] [datetime] NULL,
	[Comment_CreatedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Comment_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Doctor]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Doctor](
	[Doctor_Id] [int] NOT NULL,
	[Specialization_Id] [int] NOT NULL,
	[Doctor_Bio] [nvarchar](max) NULL,
	[Doctor_Status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Doctor_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Doctor_schedule]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Doctor_schedule](
	[Slot_Id] [int] NOT NULL,
	[Doctor_Id] [int] NOT NULL,
	[Schedule_WorkDate] [date] NOT NULL,
	[Schedule_Status] [nvarchar](50) NULL,
	[Schedule_Note] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[drug]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[drug](
	[Drug_Id] [int] IDENTITY(1,1) NOT NULL,
	[Drug_Name] [nvarchar](255) NOT NULL,
	[Drug_Description] [nvarchar](max) NULL,
	[Drug_CreateDate] [datetime] NULL,
	[Drug_ExpiryDate] [date] NULL,
	[Drug_Quantity] [int] NULL,
	[CategoryDrug_Id] [int] NULL,
	[Specialization_Id] [int] NULL,
	[status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Drug_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[Reservation_Id] [int] NOT NULL,
	[Feedback_CreatedBy] [int] NOT NULL,
	[Doctor_Id] [int] NOT NULL,
	[Feedback_Content] [nvarchar](max) NULL,
	[Star_Rating] [tinyint] NULL,
	[Feedback_CreatedDate] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[Reservation_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Medicine]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Medicine](
	[Reservation_Id] [int] NOT NULL,
	[Drug_Name] [nvarchar](255) NOT NULL,
	[Quantity] [nvarchar](50) NULL,
	[Note_Medical] [nvarchar](max) NULL,
 CONSTRAINT [PK_Medicine] PRIMARY KEY CLUSTERED 
(
	[Reservation_Id] ASC,
	[Drug_Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Relationship]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Relationship](
	[Relationship_Id] [int] IDENTITY(1,1) NOT NULL,
	[Relationship_Fullname] [nvarchar](100) NOT NULL,
	[Relationship_Phone] [nvarchar](20) NOT NULL,
	[Relationship_Email] [nvarchar](255) NOT NULL,
	[Relationship_Gender] [tinyint] NULL,
	[Relationship_CreatedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Relationship_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reservation]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservation](
	[Reservation_Id] [int] IDENTITY(1,1) NOT NULL,
	[Reservation_Fullname] [nvarchar](100) NULL,
	[Reservation_Email] [nvarchar](255) NULL,
	[Reservation_Phone] [nvarchar](20) NULL,
	[Reservation_Address] [nvarchar](255) NULL,
	[Reservation_Date] [date] NULL,
	[Reservation_Gender] [tinyint] NULL,
	[Reservation_Assign] [tinyint] NULL,
	[Reservation_Status] [nvarchar](50) NULL,
	[Reservation_Note] [nvarchar](max) NULL,
	[Reservation_NoteByDoctor] [nvarchar](max) NULL,
	[Reservation_CreatedDate] [date] NULL,
	[Reservation_CreatedBy] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Reservation_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reservation_Details]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reservation_Details](
	[Reservation_Id] [int] NOT NULL,
	[Account_Id] [int] NULL,
	[Relationship_Id] [int] NULL,
	[Doctor_Id] [int] NOT NULL,
	[Slot_Id] [int] NOT NULL,
	[Schedule_WorkDate] [date] NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Slot]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Slot](
	[Slot_Id] [int] IDENTITY(1,1) NOT NULL,
	[Start_Time] [time](7) NULL,
	[End_Time] [time](7) NULL,
PRIMARY KEY CLUSTERED 
(
	[Slot_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Specialization]    Script Date: 6/6/2025 7:49:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Specialization](
	[Specialization_Id] [int] IDENTITY(1,1) NOT NULL,
	[Specialization_Name] [nvarchar](100) NOT NULL,
	[Specialization_Image] [nvarchar](255) NOT NULL,
	[Specialization_Status] [nvarchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[Specialization_Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[Account] ON 

-- Add Blog_Content column to Blog table
ALTER TABLE [dbo].[Blog]
ADD [Blog_Content] [nvarchar](max) NULL;
GO

-- Update existing records to copy Blog_Description to Blog_Content
UPDATE [dbo].[Blog]
SET [Blog_Content] = [Blog_Description]
WHERE [Blog_Content] IS NULL;
GO 