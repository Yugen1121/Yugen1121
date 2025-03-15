import sqlite3 as sql

conn = sql.connect("database.db")

cur = conn.cursor()

cur.execute("""
    CREATE TABLE IF NOT EXISTS Hotel(
            Hotel_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Name VARCHAR(128),
            Location VARCHAR(128),
            Rating INTEGER);
""")

cur.execute("""
    CREATE TABLE IF NOT EXISTS Guest(
            Guest_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Name VARCHAR(128),
            Email VARCHAR(128),
            Age INTEGER);
""")

cur.execute("""
    CREATE TABLE IF NOT EXISTS Room_Types(
            Room_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            Name VARCHAR(128),
            Description VARCHAR(128),
            Price FLOAT
            );
""")

cur.execute("""
    CREATE TABLE IF NOT EXISTS Rooms(
            Hotel_ID INTEGER,
            Type_ID INTEGER,
            Room_number INTEGER PRIMARY KEY AUTOINCREMENT,
            CAPACITY INTEGER,
            FOREIGN KEY(Hotel_ID) REFERENCES Hotel(Hotel_ID),
            FOREIGN KEY(Type_ID) REFERENCES  Room_Types(Room_ID)
            )
""")

cur.execute("""
    CREATE TABLE IF NOT EXISTS Reservations(
            Guest_ID INTEGER,
            Room_ID INTEGER,
            Check_IN DATE,
            Check_OUT DATE,
            FOREIGN KEY (Guest_ID) REFERENCES Guest(Guest_ID),
            FOREIGN KEY (Room_ID) REFERENCES Rooms(Room_number)
            )
""")

Hotel = []

rooms = [
    [1, 1, 101, 2],
    [1, 1, 102, 2],
    [2, 2, 201, 3],
    [2, 2, 202, 4],
    [3, 1, 301, 2]
]


room_types = [
    ["Standard", "Standard room with basic amenities", "100.00"],
    ["Deluxe", "Spacious room with additional amenities", "150.00"],
    ["Suite", "Luxurious suite with separate living area", "250.00"]
]

Guest = []
Reservations = []

cur.executemany("INSERT INTO Hotel(Name, Location, Rating) VALUES (?, ?, ?);", Hotel)
cur.executemany("INSERT INTO Rooms(Hotel_ID, Type_ID, Room_number, Room_number, Capacity) VALUES (?, ?, ?, ?)", )