import json
import random
import sys

def load_json_inputs() -> dict:
    return json.load(sys.stdin)

def load_file(filename: str) -> dict:
    with open(filename) as file:
        return json.load(file)

def initial_R0(R0_min: float, R0_max: float) -> float:
    return R0_min + (R0_max - R0_min) * random_perc(1)

def mask(R: float, usage_perc: float, effectiveness: float) -> float:
    efficiency = usage_perc * effectiveness
    return R * (1-efficiency)

def mask_usage_rise(up_limit: float, cur_case: int, TOTAL_PPL: int, day: int) -> float:
    
    e1 = pow(day/100,0.1) if day < 100 else 1
    e2 = pow((cur_case/TOTAL_PPL),0.1)
    return up_limit * e1 * e2

def vaccine(R: float, availability_perc: float, efficacy: float) -> float:
    efficiency = availability_perc * efficacy
    return R * (1-efficiency)

def vaccine_avail_raise(vaccine_up_limit: float, days: int, created_day: int, all_injected_day: int) -> float:
    if days < created_day:
        return 0
    elif days < all_injected_day:
        r = (1+random_perc(0.01)) * (days-created_day)/(all_injected_day-created_day)
        r = r if r < 1 else 1
        return r*vaccine_up_limit
    else:
        return vaccine_up_limit

def barrel(R: float, **barrels) -> float:
    for efficiency in barrels.values():
        R = R * (1-efficiency)
    return R

def testAndTrace(R: float, test_perc: float, test_effi: float, trace_perc: float, trace_effi: float) -> float:
    test = test_perc*test_effi 
    trace = trace_perc*trace_effi
    return R * (1-test) * (1-trace)

# Population limits the virus spread
def ppl_limit(R: float, current_case: int, TOTAL_PPL: int) -> float:
    return R * (1 - pow(current_case/TOTAL_PPL,0.015))

def add_case(R: float, current_case: int, TOTAL_PPL: int) -> float:
    new_case = int(current_case*R)
    return new_case if new_case + current_case <= TOTAL_PPL else TOTAL_PPL - current_case

# Patients will cure after certain days
def cure(new_case_arr: list, day: int, cure_perc: float, cure_need_days: int, TOTAL_case: int) -> int:
    if day < cure_need_days:
        return 0
    else:
        cases =  int(new_case_arr[day-cure_need_days]*cure_perc)
        return cases if cases < TOTAL_case else TOTAL_case

def cure_perc_raise(cure_perc: float, barrier_quar_patient: float)->float:
    cure_perc = cure_perc * (1+barrier_quar_patient)
    if cure_perc > 1:
        return 1
    else:
        return cure_perc

# To mock patients cure except normal cure_need_days. Patients still have chance to recover from illness.
def random_cure_case(cur_case: int) -> int:
    upper_limit_perc = 0.05
    return int(cur_case * random_perc(upper_limit_perc))

# Produce a decimal between 0 and upper_limit
def random_perc(upper_limit: float):
    steps = 10
    step = random.randint(0,steps)
    return upper_limit * step/steps