import sqlite3 as sql

conn = sql.connect("database.db")

cur = conn.cursor()

items = [
    ["Melinda Brown", "Project Manager"],
    ["Peter Seller", "Team Leader"],
    ["James Douglas", "Software Developer"],
    ["Diana Gulliver", "Intern Web Designer"]
]

cur.execute("Create table if not exists employees(name varchar(128), job_title varchar(128));")

cur.executemany("insert into employees(name, job_title) values(?, ?)", items)

cur.execute("""Select *,
            CASE
                when employees.job_title = 'Project Manager' then 'Senior level'
                when employees.job_title = 'Team Leader' then 'Mid level'
                when employees.job_title = 'Software Developer' then 'Junior level'
            Else 'Other'
            end as job_title
            from employees;""")

conn.commit()

x = cur.fetchall()
print(x)

cur.close()
conn.close()

