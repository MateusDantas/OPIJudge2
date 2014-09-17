import sandbox
from sandbox import *
import os

fo = open("input.txt","r")
s = Sandbox("../../../source-codes/1",stdin = fo.fileno(), quota = dict(cpu=500,memory=63000000))
s.run()
print s.result
var =  s.probe()
#result_name = dict((getattr(Sandbox, 'S_RESULT_%s' % r), r) for r in
#        ('PD', 'OK', 'RF', 'RT', 'TL', 'ML', 'OL', 'AT', 'IE', 'BP'))
print var
print var['mem_info']
