import MySQLdb as sql
import TestClass, RunCode
from TestClass import *
from RunCode import *
import logging
import platform, re, os, shutil, sys, thread, time, urllib, SocketServer, subprocess, resource, math
logging.basicConfig(filename='error.log',level=logging.DEBUG)
## RUN JUDGE ##
def RunSub( submission_id ):
	try:

		link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database)
		cursor = link.cursor(sql.cursors.DictCursor)

		cursor.execute("SELECT * FROM SUBMISSION WHERE ID = '" + str(submission_id) + "'") ## QUERY FOR PROBLEM ID
		run_inf = cursor.fetchone()
		problem_id = run_inf['PROBLEMID'] ## PROBLEM ID
		code_extension = run_inf['LANGUAGE'] ## CODE EXTENSION
		path_to_problem = path_problems + "%d/" % (problem_id)
		## END SUBMISSION INFORMATION ##

		## DO TESTS ##
		test = Test()
		test.parse_file(path_to_problem + "testplan.txt")
		result = CompileCode(submission_id, code_extension)
		total_ac = 0
		outp_text = ""
		final_result = ""

		if result != CE_SIGNAL:
			for testset in test.test_sets:
				fail = False
				for case_id in testset:
					time_limit = test.test_cases[case_id].time_limit
					memory_limit = test.test_cases[case_id].memory_limit
					input_file = test.test_cases[case_id].input_file
					output_file = test.test_cases[case_id].output_file
					judge_file = test.test_cases[case_id].judge_file
					answer = RunTestCase(time_limit, memory_limit, path_to_problem + input_file, path_to_problem +output_file, judge_file, submission_id, code_extension)
					if answer != "OK":
						outp_text += answer + ";"
						final_result = answer
						fail = True
						break

				if fail == False:
					total_ac += 1
					outp_text += "OK;"

		else:
			outp_text += "CE;"
			final_result = "CE"

		## END TESTS
		total_points = float(total_ac) / float(len(test.test_sets))
		total_points *= 100.0
		total_points = int(total_points)

		if total_points == 100:
			final_result = "AC"

		cursor.execute("UPDATE SUBMISSION SET POINTS = " + str(total_points) + " , STATUS = '" + final_result + "' , DETAILS = '" + outp_text + "' WHERE ID = " + str(submission_id))
		try: cursor.close()
		except: pass
		try: link.close()
		except: pass

	except sql.Error, e:
            logging.warning("MySQL Error %d : %s\n" % (e.args[0],e.args[1]))
	
## END RUN JUDGE ##

class MyTCPHandler( SocketServer.StreamRequestHandler ):

	def handle(self):
		self.data = self.rfile.readline().strip()
		now_data = self.data.split(':')

		if now_data[-1] != server_secret_key:
			return		

		if now_data[0] == "judge":
			logging.warning("Judging Submission[%d]" % (int(now_data[1])))
			RunSub(int(now_data[1]))
		elif now_data[1] == "sub" and now_data[2] == "all":
			logging.warning("Rejudging All Submissions")
			link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database)
			cursor = link.cursor(sql.cursors.DictCursor)
			cursor.execute("SELECT * FROM SUBMISSION ORDER BY ID ASC")
			rows = cursor.fetchall()
			for row in rows:
				RunSub(int(row['ID']))
			cursor.close()
			link.close()	
		elif now_data[1] == "sub":
			logging.warning("Rejuding Submission[%d]" % (int(now_data[2])))
			RunSub(int(now_data[2]))
		elif now_data[1] == "prob":
			logging.warning("Rejuding submissions from problem[%d]" % (int(now_data[2])))
			link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database)
			cursor = link.cursor(sql.cursors.DictCursor)
			cursor.execute("SELECT * FROM SUBMISSION WHERE PROBLEMID=" + now_data[2] + " ORDER BY ID ASC")
			rows = cursor.fetchall()
			for row in rows:
				RunSub(int(row['ID']))
			cursor.close()
			link.close()

if __name__ == "__main__":

	logging.debug("OPI Judge\n")
	HOST, PORT = "localhost", 8722
	server = SocketServer.TCPServer((HOST, PORT), MyTCPHandler)
	server.request_queue_size = 200
	logging.debug('Queue Size: %d' % (server.request_queue_size ))
	logging.debug('Submissions...')
	
	try:
		server.serve_forever()
	except KeyboardInterrupt, e:
		logging.warning("Keyboard Interrupt Detected.\n")
	except Exception, e:
		logging.warning("Exception : " + str(e) + "\n")
