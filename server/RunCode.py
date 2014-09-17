import platform, re, os, shutil, sys, thread, time, urllib, SocketServer, subprocess, resource, math
import signal
import math
from sandbox import *
from sandboxpolicy import SelectiveOpenPolicy
import logging

## DEFINE VARIABLES ##
server_secret_key = "309E127J-D021K7S21D7JE9-D#@873214#%DS-odulas029318-*-*"
sql_hostname = "localhost"
sql_hostport = 3306
sql_username = "root"
sql_password = ""
sql_database = "opijudge"
path_codes = "/home/mdantas/codes-opijudge/"
path_problems = "/home/mdantas/problems-opijudge/"
path_submission = "/home/mdantas/runs-opijudge/"
ioeredirect = "2>error-sub.log"
TLE_SIGNAL = -1
MLE_SIGNAL = 1
RTE_SIGNAL = -2
AC_SIGNAL = 0
WA_SIGNAL = 3
CE_SIGNAL = 4
executable_file = ""
##############



logging.basicConfig(filename='error.log',level=logging.DEBUG)
def file_read(filename):
	if not os.path.exists(filename): return "";
	f = open(filename,"r"); d = f.read(); f.close(); return d.replace("\r","")

def file_write(filename,data):
	f = open(filename,"w"); f.write(data.replace("\r","")); f.close();

def file_prepender(filename, line):
	with open(filename, 'r+') as f:
		content = f.read()
		f.seek(0, 0)
		f.write(line.rstrip('\r\n') + '\n' + content)

def cleanAll():
	os.system("rm -rf " + path_codes + "*.out")
	os.system("rm -rf " + path_codes + "*.class")
	os.system("rm -rf " + path_codes + "*.jar")
	os.system("rm -rf " + path_codes + "*.pyc")

def CompileCode( submission_id, language ):

	if( language not in ('c', 'cpp', 'pas', 'java', 'py') ): return "CE"
	result = None

	cleanAll()

	logging.warning("COMPILE CLEAN")
	if language == "c":
		os.system("gcc " + path_codes + str(submission_id) + ".c -lm -lcrypt -O2 -pipe -ansi -ONLINE_JUDGE -w")
		os.system("mv a.out " + path_codes[:-1])
		executable_file = "a.out"
	elif language == "cpp":
		os.system("g++ " + path_codes + str(submission_id) + ".cpp")
		os.system("mv a.out " + path_codes[:-1])
		executable_file = "a.out"
	elif language == "pas":
		os.system("fpc " + path_codes + str(submission_id) + ".pas")
	elif language == "java":
		os.system("javac " + path_codes + str(submission_id) + ".java")
		os.system("bash " + path_codes + "compile_java.sh")
		executable_file = "main.jar"
	elif language == "py":
		file_prepender(path_codes + str(submission_id) + ".py", "#!/usr/bin/env python")
		os.system("python -m py_compile " + path_codes + str(submission_id) + ".py")
		os.chmod(path_codes + str(submission_id) + ".pyc", 0755)
		os.chmod(path_codes + str(submission_id) + ".py", 0755)
		executable_file = str(submission_id) + ".py"
	
	logging.warning("COMPILE FINISHED")
	if not os.path.exists(path_codes + executable_file): 
		result = CE_SIGNAL
	
	return result

def getExecutableFile(language, submission_id):
	if language == "c" or language == "cpp":
		return "a.out"
	elif language == "py":
		return str(submission_id) + ".pyc"
	else:
		return "main.jar"

def ExecuteCode( submission_id, time_limit, memory_limit, input_file, language ):

	inputfile = open(input_file, "r")
	outputfile = open(path_submission + "output_user.txt", "w")
	arg = []
	arg.append(path_codes + getExecutableFile(language, submission_id))
	
	memory_additional = 0
	time_additional = 1
	if language == "java":
		memory_additional = 1

	if language == "py" or language == "java":
		time_additional = 2

	results = dict((getattr(Sandbox,'S_RESULT_%s' % i), i) for i in \
	('PD','OK','RF','RT','TL','ML','OL','AT','IE','BP'))

	sandbox_config = {
		'args': arg,
		'stdin': inputfile.fileno(),
		'stdout': outputfile.fileno(),
		'quota': dict(wallclock=int(time_limit) * time_additional,
			cpu=int(time_limit) * time_additional,
			memory=max(int(memory_limit) * 1048576, 2000 * 1048576 * memory_additional),
			disk=10000000)}

	sand_run = Sandbox(**sandbox_config)
	sand_run.policy = SelectiveOpenPolicy(sand_run,["/usr/lib/x86_64-linux-gnu","bootstrap/jre/lib/amd64","/usr/lib/jvm","/usr/local/lib/python2.7","/etc/passwd","/etc/nsswitch.conf","/proc/meminfo","/usr/lib/python2.7","/lib/x86_64-linux-gnu","/lib/x86_64-linux-gnu/libpthread.so.0","/lib/x86_64-linux-gnu/libm.so.6","/lib/x86_64-linux-gnu/libgcc_s.so.1","/home/mdantas","/etc/ld.so.cache","/lib/x86_64-linux-gnu/libc.so.6","/usr/lib/i386-linux-gnu/libstdc++.so.6",path_problems[:-1]],[path_submission[:-1]])
	sand_run.run()
	var_result = results.get(sand_run.result,'NA')
	return var_result

def remove_extra_lines(s):
	if len(s) == 0: 
		return s
	i = -1
	while s[i] == "\n":
		i -= 1
	if i < -1:
		s = s[:i+1]
	return s

def RunTestCase( time_limit, memory_limit, input_file, output_file, judge_file, submission_id, language ):

	result = ExecuteCode(submission_id,time_limit,memory_limit,input_file,language)
	if result != "OK":
		return result

	correct_output = remove_extra_lines(file_read(output_file).replace("\r",""))
	user_output = remove_extra_lines(file_read(path_submission + "/output_user.txt").replace("\r",""))
	print correct_ouput
	print user_output

	if correct_output != user_output:
		return "WA"
	return "OK"
