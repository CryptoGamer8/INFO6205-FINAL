from Python.main.main import TOTAL_PPL
import unittest
from main.func import *

WORKSPACE_DIR = "/Users/Cyxzk/Documents/Documents/NEU/course/INFO6205_Algorithms/INFO6205-FINAL"

class TestPandemicSimulation(unittest.TestCase):

    def test_load_json_file(self):
        filename = WORKSPACE_DIR+"/config/config.json"
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
        pass

    def test_ppl_limit(self):
        R1, R2, R3 = 2, 3, 4
        current_case1, current_case2, current_case3 = 300, 8000, 5000
        TOTAL_PPL = 10000
        R1_expected = ppl_limit(R1, current_case1, TOTAL_PPL)
        R2_expected = ppl_limit(R2, current_case2, TOTAL_PPL)
        R3_expected = ppl_limit(R3, current_case3, TOTAL_PPL)
        self.assertTrue( 0<= R1_expected <= R1 * (current_case1/TOTAL_PPL), "People limitation effect is over level!")
        self.assertTrue( 0<= R2_expected <= R2 * (current_case2/TOTAL_PPL), "People limitation effect is over level!")
        self.assertTrue( 0<= R3_expected <= R3 * (current_case3/TOTAL_PPL), "People limitation effect is over level!")


if __name__ == '__main__':
    unittest.main()
