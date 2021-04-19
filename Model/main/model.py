from .func import *

def main():
    config = load_json_inputs()

    R0 = config["VIRUS"]["R0"]
    INITIAL_CASE = config["INITIAL_CASE"]
    TOTAL_PPL = config["TOTAL_PPL"]
    MASK = config["MASK"]
    VACCINE = config["VACCINE"]
    BARRIER = config["BARRIER"]
    TEST_AND_TRACE = config["TEST_AND_TRACE"]
    CURE = config["CURE"]
    CYCLEs = config["CYCLEs"]

    case = INITIAL_CASE
    new_case_arr = [0]
    total_case_arr = [case]
    cure_case_arr = [0]
    mask_arr = [0]
    vaccine_arr = [0]

    for day in range(1,CYCLEs):
        R = R0
    # mask
        mask_up_limit = MASK["up_limit"]
        mask_effi = MASK["effectiveness"]
        mask_usage_perc = mask_usage_rise(mask_up_limit, case, TOTAL_PPL, day)
        R = mask(R, mask_usage_perc, mask_effi)
        mask_arr.append(int(mask_usage_perc*TOTAL_PPL))
    # vaccine
        vaccine_up_limit = VACCINE["up_limit"]
        vaccine_effi = VACCINE["efficacy"]
        vaccine_created = VACCINE["created_day"]
        vaccine_all_injected = VACCINE["all_injected_day"]
        vaccine_avai = vaccine_avail_raise(vaccine_up_limit, day, vaccine_created, vaccine_all_injected)
        R = vaccine(R, vaccine_avai,vaccine_effi)
        vaccine_arr.append(int(vaccine_avai*TOTAL_PPL))
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
        R = ppl_limit(R, case, TOTAL_PPL)
        new_case = add_case(R,case, TOTAL_PPL)
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
        cure_case_arr.append(cure_case+random_cure_cases)
        total_case_arr.append(case)

    outputs = {
        "cycles": CYCLEs,
        "new_cases": new_case_arr,
        "cure_cases": cure_case_arr,
        "total_cases": total_case_arr,
        "mask_usage": mask_arr,
        "vaccine_avail": vaccine_arr
    }

    print(json.dumps(outputs))