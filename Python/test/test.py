import sys
import os

# PACKAGE_PARENT = "../Python"
# SCRIPT_DIR = os.path.dirname(os.path.realpath(os.path.join(os.getcwd(), os.path.expanduser(__file__))))
# sys.path.append(os.path.normpath(os.path.join(SCRIPT_DIR, PACKAGE_PARENT)))

import unittest
from main.func import *

class TestPandemicSimulation(unittest.TestCase):

    def test_load_json_file(self):
        filename = "../config/config.json"
        data = load_file(filename=filename)
        self.assertTrue(data is not None, "Loaded data is None")
        self.assertTrue(type(data) is dict,"Loaded data is not dictionary.")

    def test_initial_R0(self):
        R1_min, R1_max = 2, 3
        R2_min, R2_max = 1.8, 3.6
        R1_expected = initial_R0(R1_min,R1_max)
        R2_expected = initial_R0(R2_min,R2_max)
        for i in range(10):
            self.assertTrue(R1_min <= R1_expected <= R1_max)
            self.assertTrue(R2_min <= R2_expected <= R2_max)

    def test_mask(self):
        R1, R2, R3 = 2, 3, 4
        usage_perc1, usage_perc2, usage_perc3 = 0.1, 0.2, 0.3
        effectiveness = 0.5
        R1_expected = mask(R1, usage_perc1, effectiveness)
        R2_expected = mask(R2, usage_perc2, effectiveness)
        R3_expected = mask(R3, usage_perc3, effectiveness)
        self.assertTrue(0 <= R1_expected <= R1, "Mask effect not correct.")
        self.assertTrue(0 <= R2_expected <= R2, "Mask effect not correct.")
        self.assertTrue(0 <= R3_expected <= R3, "Mask effect not correct.")


    def test_mask_usage_rise(self):
        usage1, usage2 = 0.2, 0.6
        cur_case1, cur_case2 = 100, 150
        TOTAL_PPL = 200
        expected1 = mask_usage_rise(usage1, cur_case1, TOTAL_PPL)
        expected2 = mask_usage_rise(usage2, cur_case2, TOTAL_PPL)
        self.assertTrue(usage1<=expected1<=1, "Mask usage rise not correct")
        self.assertTrue(usage2<=expected2<=1, "Mask usage rise not correct")

    def test_vaccine(self):
        R1, R2, R3 = 2, 3, 4
        availability_perc1, availability_perc2, availability_perc3 = 0.1, 0.2, 0.3
        effectiveness = 0.8
        R1_expected = vaccine(R1, availability_perc1, effectiveness)
        R2_expected = vaccine(R2, availability_perc2, effectiveness)
        R3_expected = vaccine(R3, availability_perc3, effectiveness)
        self.assertTrue(0 <= R1_expected <= R1, "Vaccine effect not correct.")
        self.assertTrue(0 <= R2_expected <= R2, "Vaccine effect not correct.")
        self.assertTrue(0 <= R3_expected <= R3, "Vaccine effect not correct.")

    def test_vaccine_avail_raise(self):
        vaccine_avail = 0.5
        days1, days2, days3 = 0, 100, 500
        created_day = 300
        all_injected_day = 480
        expected1 = vaccine_avail_raise(vaccine_avail,days1,created_day,all_injected_day)
        expected2 = vaccine_avail_raise(vaccine_avail,days2,created_day,all_injected_day)
        expected3 = vaccine_avail_raise(vaccine_avail,days3,created_day,all_injected_day)
        self.assertTrue(0 <= expected1 <= 1, "Vaccine rise not correct")
        self.assertTrue(0 <= expected2 <= 1, "Vaccine rise not correct")
        self.assertTrue(0 <= expected3 <= 1, "Vaccine rise not correct")

    def test_ppl_limit(self):
        R1, R2, R3 = 2, 3, 4
        current_case1, current_case2, current_case3 = 300, 8000, 5000
        TOTAL_PPL = 10000
        R1_expected = ppl_limit(R1, current_case1, TOTAL_PPL)
        R2_expected = ppl_limit(R2, current_case2, TOTAL_PPL)
        R3_expected = ppl_limit(R3, current_case3, TOTAL_PPL)
        self.assertTrue( 0<= R1_expected <= R1 , "People limitation effect is over level!")
        self.assertTrue( 0<= R2_expected <= R2 , "People limitation effect is over level!")
        self.assertTrue( 0<= R3_expected <= R3 , "People limitation effect is over level!")

    def test_cure(self):
        new_case_arr = [1,5,10,20,50,70,100]
        day1, day2 = 2,10
        cure_perc = 0.5
        cure_need_days = 5
        TOTAL_case1, TOTAL_case2 = 30, 100
        expected1 = cure(new_case_arr, day1, cure_perc, cure_need_days, TOTAL_case1)
        expected2 = cure(new_case_arr, day2, cure_perc, cure_need_days, TOTAL_case2)
        self.assertTrue(0 <= expected1 <= TOTAL_case1, "Cure function not correct")
        self.assertTrue(0 <= expected2 <= TOTAL_case2, "Cure function not correct")

    def test_cure_perc_raise(self):
        cure_perc = 0.5
        barrier_effect1, barrier_effect2 = 0.1, 1
        expected1 = cure_perc_raise(cure_perc,barrier_effect1)
        expected2 = cure_perc_raise(cure_perc,barrier_effect2)
        self.assertTrue(cure_perc <= expected1 <= 1, "Cure perc raise not correct")
        self.assertTrue(cure_perc <= expected2 <= 1, "Cure perc raise not correct")


if __name__ == '__main__':
    unittest.main()