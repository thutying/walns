import psycopg2
import sys
import subprocess

# operate postgresql 
class pts:
    def __init__(self):
        pass
    
    def main(self,infile):
        """
        加密入口
        :param infile
        :return none 
        """
        pass
    
    
# encode files
class encode:
    def __init__(self):
        pass 
        
        
        
if __name__=='__main__':
    pass 
    
#连接数据库
conn = psycopg2.connect(
    host="localhost",
    database="walns",
    user="walns")#,
    #password="holyshit")

#创建一个游标对象
cur = conn.cursor()

#执行SQL查询
cur.execute("SELECT * FROM key")

#获取查询结果
rows = cur.fetchall()

#打印结果
for row in rows:
    print(row)

#关闭连接和游标对象
cur.close()
conn.close()
