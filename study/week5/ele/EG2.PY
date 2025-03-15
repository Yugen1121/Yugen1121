import sqlite3 as sql

conn = sql.connect("database.db")
cur = conn.cursor()


items = [
    ("Laptop", 5, 1200),
    ("Smartphone", 10, 800),
    ("Tablet", 15, 300),
    ("Headphones", 20, 150),
    ("Charger", 50, 25),
    ("Laptop", 5, 1200),
    ("Smartphone", 10, 800),
    ("Tablet", 15, 300),
    ("Headphones", 20, 150),
    ("Charger", 50, 25)
]

cur.execute("Create table if not exists sales(name varchar(128), quantity integer, price float);")

cur.executemany("insert into sales(name, quantity, price) values(?, ?, ?)", items)

conn.commit()
cur.execute("SELECT *, quantity*price as revenue from sales")
x = cur.fetchall()
print(x)
cur.close()
conn.close()
