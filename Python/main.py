import json
import random
from main.func import *

WORKSPACE_DIR = "/Users/Cyxzk/Documents/Documents/NEU/course/INFO6205_Algorithms/INFO6205-FINAL"
filename = WORKSPACE_DIR+"/config/config.json"
config = load_file(filename)

R0_range = config["VIRUS"]["Covid"]["R0"]
R0 = initial_R0(R0_range[0],R0_range[1])
INITIAL_CASE = config["INITIAL_CASE"]
TOTAL_PPL = config["TOTAL_PPL"]
MASK = config["MASK"]
VACCINE = config["VACCINE"]
BARRIER = config["BARRIER"]
TEST_AND_TRACE = config["TEST_AND_TRACE"]
CURE = config["CURE"]
CYCLEs = 100

case = INITIAL_CASE
new_case_arr = [0]
totoal_case_arr = [case]
cure_case_arr = [0]

for day in range(CYCLEs):
    R = R0
   # mask
    mask_usage_perc = MASK["usage_perc"]
    mask_effi = MASK["effectiveness"]
    mask_usage_perc = mask_usage_rise(mask_usage_perc, case, TOTAL_PPL)
    R = mask(R, mask_usage_perc, mask_effi)
   # vaccine
    vaccine_avai = VACCINE["availability_perc"]
    vaccine_effi = VACCINE["efficacy"]
    vaccine_avai = vaccine_avail_raise(vaccine_avai, day)
    R = vaccine(R, vaccine_avai,vaccine_effi)
   # barrier
    barrier_quar_suscep = BARRIER["quarantine"]["susceptible"]
    barrier_vent = BARRIER["ventilation"]
    R = barrel(R, quarntine = barrier_quar_suscep, ventilation = barrier_vent)
   # test and trace
    test_perc = TEST_AND_TRACE["test_perc"]
    test_effi = TEST_AND_TRACE["test_effi"]
    trace_perc = TEST_AND_TRACE["trace_perc"]
    trace_effi = TEST_AND_TRACE["trace_effi"]
    R = testAndTrace(R, test_perc, test_effi, trace_perc, trace_effi)
   # population limit
    if(day==60):
        print("hello")
    
    R = ppl_limit(R, case, TOTAL_PPL)
    new_case = int(case*R)
    case += int(new_case)

    # cure people from patiens after cure_need_days
    # cure perc raises if we do good quarantine and treatment of patients
    cure_perc = CURE["cure_perc"]
    barrier_quar_patient = BARRIER["quarantine"]["patient"]
    cure_perc = cure_perc_raise(cure_perc, barrier_quar_patient)
    cure_need_days = CURE["cure_need_days"]
    cure_case = cure(new_case_arr, day, cure_perc, cure_need_days, case)
    case -= cure_case
    
    random_cure_cases = random_cure_case(case)
    case -= random_cure_cases

    new_case_arr.append(new_case)
    cure_case_arr.append(cure_case)
    totoal_case_arr.append(case)

print(new_case_arr)
print(cure_case_arr)
print(totoal_case_arr)