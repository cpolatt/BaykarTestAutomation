import time
from locust import HttpUser, task, between
import logging

log_format = "%(asctime)s - %(levelname)s - %(module)s - %(lineno)d - %(message)s"
date_format = "%Y-%m-%d %H:%M:%S"
logging.basicConfig(format=log_format, datefmt=date_format, level=logging.DEBUG)

class PagePerformanceUser(HttpUser):
    wait_time = between(1, 5)
    host = "https://kariyer.baykartech.com"
    response_time_warning_threshold = 2000

    def perform_get_request(self, url, page_name):

        start_time = time.time()
        response = self.client.get(url, name=page_name)
        end_time = time.time()
        response_time = int((end_time - start_time) * 1000)

        if response.status_code != 200:
            logging.error(f"Error! {page_name} page encountered an issue. Status Code: {response.status_code}")

        self.check_response_time(page_name, response_time)

    def check_response_time(self, page_name, response_time):

        if response_time > self.response_time_warning_threshold:
            logging.warning(f"Warning! {page_name} is too slow: {response_time} ms > {self.response_time_warning_threshold} ms")

    @task
    def yuksek_irtifa_page(self):
        self.perform_get_request("/en/yuksek-irtifa/", "High Altitude Page (EN)")

    @task
    def internship_page(self):
        self.perform_get_request("/en/internship/", "Internship Page")

    @task
    def sss_page(self):
        self.perform_get_request("/en/sss/", "FAQ Page")

    @task
    def login_page(self):
        self.perform_get_request("/en/hesaplar/login/?next=/en/dashboard/", "Login Page")
