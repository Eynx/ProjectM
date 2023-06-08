-- Initialization script for the ProjectZ database

-- Create the sequencers to increment the table primary keys
CREATE SEQUENCE "Artists_ID_seq" MAXVALUE 1000;
CREATE SEQUENCE "Albums_ID_seq" MAXVALUE 1000;
CREATE SEQUENCE "Formats_ID_seq" MAXVALUE 1000;
CREATE SEQUENCE "Genres_ID_seq" MAXVALUE 1000;
CREATE SEQUENCE "Songs_ID_seq" MAXVALUE 10000;

-- Create the list of artists table
CREATE TABLE "Artists"
(
	"ID" INTEGER NOT NULL DEFAULT nextval('"Artists_ID_seq"'),
	"Name" TEXT NOT NULL,
	CONSTRAINT "Artists_ID_pk" PRIMARY KEY ("ID"),
	CONSTRAINT "Artists_Name_unique" UNIQUE ("Name")
)

-- Create the list of albums table
CREATE TABLE "Albums"
(
	"ID" INTEGER NOT NULL DEFAULT nextval('"Albums_ID_seq"'),
	"Name" TEXT NOT NULL,
	CONSTRAINT "Albums_ID_pk" PRIMARY KEY ("ID"),
	CONSTRAINT "Albums_Name_unique" UNIQUE ("Name")
)

-- Create the list of  file formats table
CREATE TABLE "Formats"
(
	"ID" INTEGER NOT NULL DEFAULT nextval('"Formats_ID_seq"'),
	"Extension" TEXT NOT NULL,
	CONSTRAINT "Formats_ID_pk" PRIMARY KEY ("ID"),
	CONSTRAINT "Formats_Extension_unique" UNIQUE ("Extension")
);

-- Create the list of song genres table
CREATE TABLE "Genres"
(
	"ID" INTEGER NOT NULL DEFAULT nextval('"Genres_ID_seq"'),
	"Name" TEXT NOT NULL,
	CONSTRAINT "Genres_ID_pk" PRIMARY KEY ("ID"),
	CONSTRAINT "Genres_Name_unique" UNIQUE ("Name")
);

-- Create the list of songs table
CREATE TABLE "Songs"
(
	"ID" INTEGER NOT NULL DEFAULT nextval('"Songs_ID_seq"'),
	"Title" TEXT NOT NULL,
	"Artist" INTEGER,
	"Album" INTEGER,
	"Genre" INTEGER,
	"Format" INTEGER,
	CONSTRAINT "Songs_ID_pk" PRIMARY KEY ("ID"),
	CONSTRAINT "Songs_Artist_fk" FOREIGN KEY ("Artist") REFERENCES "Artists"("ID") ON DELETE SET NULL,
	CONSTRAINT "Songs_Albums_fk" FOREIGN KEY ("Albums") REFERENCES "Albums"("ID") ON DELETE SET NULL,
	CONSTRAINT "Songs_Genre_fk" FOREIGN KEY ("Genre") REFERENCES "Genres"("ID") ON DELETE SET NULL,
	CONSTRAINT "Songs_Format_fk" FOREIGN KEY ("Format") REFERENCES "Formats"("ID") ON DELETE SET NULL
);
