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
		#logging.debug("Judging Submission = %d" % (submission_id))
		#logging.debug("Connecting to Database...\n")
		link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database)
		cursor = link.cursor(sql.cursors.DictCursor)
		#logging.debug("Connected to Database!\n")

		## GET SUBMISSION INFORMATION ##
		cursor.execute("SELECT * FROM submission WHERE id = '" + str(submission_id) + "'") ## QUERY FOR PROBLEM ID
		run_inf = cursor.fetchone()
		problem_id = run_inf['problem_id'] ## PROBLEM ID
		code_extension = run_inf['language'] ## CODE EXTENSION
		path_to_problem = path_problems + "/%d/" % (problem_id)
		## END SUBMISSION INFORMATION ##

		## DO TESTS ##
		test = Test()
		test.parse_file(path_to_problem + "info.txt")
		result = CompileCode(submission_id, code_extension)
		total_ac = 0
		outp_text = ""
		final_result = ""

		if result != CE_SIGNAL:
			for testset in test.test_sets:
				fail = False
				for case_id in testset:
					#logging.debug("running case %d ..." % int(case_id))
					time_limit = test.test_cases[case_id].time_limit
					memory_limit = test.test_cases[case_id].memory_limit
					input_file = test.test_cases[case_id].input_file
					output_file = test.test_cases[case_id].output_file
					judge_file = test.test_cases[case_id].judge_file
					answer = RunTestCase(time_limit, memory_limit, path_to_problem + input_file, path_to_problem +output_file, judge_file, submission_id)
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

		#logging.debug("Submission %s GOT %s - Total Points: %s and the Final Result: %s" % (str(submission_id),str(final_result),str(total_points),str(outp_text)))
		cursor.execute("UPDATE submission SET points = " + str(total_points) + " , result = '" + final_result + "' , result_details = '" + outp_text + "' WHERE id = " + str(submission_id))
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
		##logging.debug(self.data)
		if self.data == "rejudge-all" :
			logging.debug("Rejudging All Submissions")
			link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database,unix_socket="/opt/lampp/var/mysql/mysql.sock")
			cursor = link.cursor(sql.cursors.DictCursor)
			cursor.execute("SELECT * FROM 'submission' ORDER BY id ASC") 
			rows = cursor.fetchall()
			for row in rows:
				RunSub(int(row['id']))
			cursor.close()
			link.close()	

		elif "rejudge" in self.data and False:
			problem_id = self.data.split(';')[1]
			link = sql.connect( host = sql_hostname, port = sql_hostport, user = sql_username, passwd = sql_password, db = sql_database,unix_socket="/opt/lampp/var/mysql/mysql.sock")
			cursor = link.cursor(sql.cursors.DictCursor)
			cursor.execute("SELECT * FROM submission WHERE problem_id = '" + problem_id + "'")
			rows = cursor.fetchall()
			for row in rows:
				RunSub(int(row['id']))

		elif( len(self.data) ):
			logging.debug("Judging Submission[%d]" % ( int(self.data) ))
			RunSub(int(self.data))

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
