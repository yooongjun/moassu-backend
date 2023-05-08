#-- 페이지 1부터 3까지의 모든 공지사항 내에서 데이터 추출 --#

import requests
from bs4 import BeautifulSoup

def get_univ_notice():
  # 크롤링할 사이트의 URL: 학교 홈페이지 공지사항 첫번째 페이지
  base_url = "https://scatch.ssu.ac.kr/%EA%B3%B5%EC%A7%80%EC%82%AC%ED%95%AD/"
  urls = []

  # 크롤링 대상이 되는 페이지의 URL 추출
  crawling_url = base_url

  # 페이지 1부터 3까지 모든 페이지 내의 공지사항들의 링크 추출
  for page in range(1,4):
      crawling_url = base_url + "page/" + str(page) +"/"

      # 사이트 메인 페이지 가져오기
      try:
          # 웹 페이지 가져오기
          response = requests.get(crawling_url)
      except requests.exceptions.MissingSchema:
          print("잘못된 URL입니다.")
      else:
          # 사용 중인 인코딩 확인 후, 그에 맞게 변환하여 가져옴
          # base_url의 페이지 내의 모든 html을 문자열로 가져옴
          bs_obj = BeautifulSoup(response.text, "html.parser")

          # 각 페이지들의 제목(페이지 링크)를 나타내는 태그 내용 추출
          lis = bs_obj.select("ul.notice-lists > li > div > div.notice_col3 > a")

          # 각 페이지들의 링크 추출 후, urls 리스트 내에 저장
          for li in lis:
              url = li.get("href")
              urls.append(url)

  # 학교 공지홈페이지의 모든 데이터를 저장할 리스트
  homepage_univ = []

  # 각 공지사항(url) 내의 데이터 추출
  for url in urls:
      # 해당 url 내의 데이터들을 저장할 딕셔너리
      dic = {"url": url}

      try:
          # 웹 페이지 가져오기
          response = requests.get(url)
      except requests.exceptions.MissingSchema:
          print("잘못된 URL입니다.")
      else:
          # 사용 중인 인코딩 확인 후, 그에 맞게 변환하여 가져옴
          # url의 페이지 내의 모든 html을 문자열로 가져옴
          bs_obj = BeautifulSoup(response.text, "html.parser")

          # 공지사항 페이지에서 공지사항에 해당하는 부분만 추출
          notice = bs_obj.select_one("div.bg-white")

          # 카테고리, 제목 추출
          category = notice.select_one("span.label.small.d-inline-block.border.pl-3.pr-3").get_text()
          title = notice.select_one("h2.font-weight-light.mb-3").get_text()

          # 작성일자, 첨부파일, 내용 추출 -> <div> 태그
          datas = notice.select("div")

          date = datas[1].get_text()

          # 첨부파일 이름
          attach_name = datas[4].select("ul.download-list > li > a > span")
          attach_names = []
          for a in attach_name:
              temp = a.get_text()
              attach_names.append(temp)
          # 첨부파일
          attach = datas[4].select("ul.download-list > li > a")
          attachs = []
          for a in attach:
              temp = a.get("href")
              attachs.append(temp)

          # 공지사항 내용은 html 소스코드 그대로 저장
          # <ul class="download-list">는 첨부파일 태그이기 때문에 공지사항 내용에서 제외
          # 만약 첨부파일이 없다면(None) -> NoneType은 decompose() 불가
          content = datas[4]
          if datas[4].select_one("ul.download-list") is not None:
              datas[4].select_one("ul.download-list").decompose()

          # 딕셔너리에 추출한 데이터들 저장
          dic["title"] = title
          dic["category"] = category
          dic["date"] = date
          dic["content"] = content
          dic_attach = {}
          for attach, attach_name in zip(attachs, attach_names):
              dic_attach[attach_name] = attach
          dic["attach"] = dic_attach

          # 리스트에 추출한 데이터 저장
          homepage_univ.append(dic)
          #print(homepage_univ)

  return homepage_univ
