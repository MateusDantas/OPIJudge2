import platform, re, os, shutil, sys, thread, time, urllib, SocketServer, subprocess, resource, math
import signal
from sandbox import *
from sandboxpolicy import SelectiveOpenPolicy

## DEFINE VARIABLES ##
sql_hostname = "localhost"
sql_hostport = 3306
sql_username = "mdantas"
sql_password = "multiuso12"
sql_database = "opi_db"
path_codes = "../../../../source-codes/"
path_problems = "../../../../problems"
ioeredirect = ""
TLE_SIGNAL = -1
MLE_SIGNAL = 1
RTE_SIGNAL = -2
AC_SIGNAL = 0
WA_SIGNAL = 3
CE_SIGNAL = 4

def file_read(filename):
	if not os.path.exists(filename): return "";
	f = open(filename,"r"); d = f.read(); f.close(); return d.replace("\r","")
def file_write(filename,data):
	f = open(filename,"w"); f.write(data.replace("\r","")); f.close();
## END DEFINE ##


## COMPILE CODE ## 

def CompileCode( submission_id, language ):
	if( language not in ('c', 'cpp', 'pas') ): return "CE"
	print "Compiling Code..."
	result = None

	if os.path.exists(path_codes + str(submission_id)):
		os.system("rm -r " + path_codes + str(submission_id))
	if language == "c":
		os.system("gcc " + path_codes + str(submission_id) + ".c -lm -lcrypt -O2 -pipe -ansi -ONLINE_JUDGE -w -o " + path_codes + str(submission_id) + " " + ioeredirect)
	elif language == "cpp":
		os.system("g++ " + path_codes + str(submission_id) + ".cpp -o " + path_codes + str(submission_id) + " " + ioeredirect)
	elif language == "pas":
		os.system("fpc " + path_codes + str(submission_id) + ".pas -o " + path_codes + str(submission_id) + " " + ioeredirect)
	
	if not os.path.exists(path_codes + str(submission_id)): result = CE_SIGNAL
	
	return result
## END COMPILE ##

## RUN TEST CASES ##

def ExecuteCode( submission_id, time_limit, memory_limit, input_file ):
	os.system("chmod 550 ../../../../problems")
	os.system("chmod 750 ../../../../runs")

	inputfile = open(input_file, "r")
	outputfile = open("../../../../runs/output_user.txt","w")
	arg = []
	arg.append(path_codes + str(submission_id))
	results = dict((getattr(Sandbox,'S_RESULT_%s' % i), i) for i in \
	('PD','OK','RF','RT','TL','ML','OL','AT','IE','BP'))
	sandbox_config = {
		'args': arg,
		'stdin': inputfile.fileno(),
		'stdout': outputfile.fileno(),
		'quota': dict(wallclock=30000,
			cpu=int(time_limit),
			memory=int(memory_limit)*1000000,
			disk=10000000)}

	sand_run = Sandbox(**sandbox_config)
	sand_run.policy = SelectiveOpenPolicy(sand_run,["/etc/ld.so.cache","/lib/i386-linux-gnu/libc.so.6","../../../../runs",path_problems],["../../../../runs"])
	sand_run.run()
	var_result = results.get(sand_run.result,'NA')
	return var_result

def remove_extra_lines(s):
	i = -1
	while s[i] == "\n":
		i -= 1
	if i < -1:
		s = s[:i+1]
	return s

def RunTestCase( time_limit, memory_limit, input_file, output_file, judge_file, submission_id ):
	result = ExecuteCode(submission_id,time_limit,memory_limit,input_file)
	if result != "OK":
		return result
	correct_output = remove_extra_lines(file_read(output_file).replace("\r",""))
	user_output = remove_extra_lines(file_read("../../../../runs/output_user.txt").replace("\r",""))
	print "USER-> " + user_output
	print "CORRECT-> " + correct_output
	if correct_output != user_output:
		return "WA"
	return "OK"
