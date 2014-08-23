## TEST CASE CLASS ##
class Testcase:
	time_limit = None
	memory_limit = None
	input_file = None
	output_file = None
	judge_file = None
	result = None # "AC", "RE", "TLE", "WA"

class Test:
	test_cases = {}
	test_sets = []

	def __init__(self):
		pass

	def parse(self, lines):
		self.test_cases = {}
		self.test_sets = []

		var_map = {}
		for line in lines:
			# Breaks line into key: value
			parts = line.split(":", 1)
			if len(parts) < 2:
				continue

			# Checks if both key and value are not empty
			key = parts[0].strip()
			value = parts[1].strip()
			if len(key) == 0 or len(value) == 0:
				continue

			# Checks if it is a comment line
			if key[0] == "#":
				continue

			# Checks if it is a variable (var_name: value)
			if key[0] != "+":
				var_map[key] = value
				continue

			# It is a function (add_test_case or add_test_set)
			function = key[1:]

			# Add test set
			if function == "test_set":
				test_cases2 = []
				for test_case in value.split(" "):
					test_id = test_case.strip()
					if len(test_id) == 0:
						continue
					test_cases2.append(test_id)
				self.test_sets.append(test_cases2)

			# Add test case
			elif function == "test_case":
				test_case = Testcase()
				parts = value.split("{", 1)
				id = parts[0].strip()
				if len(id) == 0:
					continue

				test_case.time_limit = var_map.get("time_limit", None)
				test_case.memory_limit = var_map.get("memory_limit", None)
				test_case.input_file = var_map.get("input_file", None)
				test_case.output_file = var_map.get("output_file", None)
				test_case.judge_file = var_map.get("judge_file", None)

				extra_info = parts[1].strip() if len(parts) >= 2 else ""
				extra_info = "" if extra_info == "" or extra_info[-1] != "}" else extra_info[:-1]

				if extra_info != "":
					for info in extra_info.split(","):
						parts = info.split(":")
						if len(parts) < 2:
							continue
						key = parts[0].strip()
						value = parts[1].strip()

						if key == "time_limit":
							test_case.time_limit = value
						elif key == "memory_limit":
							test_case.memory_limit = value
						elif key == "input_file":
							test_case.input_file = value
						elif key == "output_file":
							test_case.output_file = value
						elif key == "judge_file":
							test_case.judge_file = value

				if test_case.input_file != None:
					test_case.input_file = test_case.input_file.replace("${id}", id)
				if test_case.output_file != None:
					test_case.output_file = test_case.output_file.replace("${id}", id)
				if test_case.judge_file != None:
					test_case.judge_file = test_case.judge_file.replace("${id}", id)
				if test_case.time_limit != None and test_case.time_limit.isdigit():
					test_case.time_limit = int(test_case.time_limit)
				if test_case.memory_limit != None and test_case.memory_limit.isdigit():
					test_case.memory_limit = int(test_case.memory_limit)

				self.test_cases[id] = test_case

	def parse_file(self, file_name):
		with open(file_name, "r") as f:
			lines = f.readlines()
		self.parse(lines)
## END TEST CASE CLASS ##
