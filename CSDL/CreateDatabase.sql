CREATE DATABASE [CARO]
GO
USE [CARO]
GO
CREATE TABLE [USER]
(
	UserName	varchar(50) PRIMARY KEY,
	Password	varchar(50) NOT NULL,
	SCORE		int,
	LastVisit	datetime
)