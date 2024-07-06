# 반기다: 반려견을 기다리며 하는 다짐
<div align="center">
  
### 반려견 예비 보호자들을 위한 스타터 애플리케이션
`#반려견 돌봄 시뮬레이션` `#가족 단위 사용`

 가족과 함께 **반려견 돌봄 가상 체험**을 통해 반려견을 키우는 데 있어 필수적인 **시간적, 심리적, 환경적, 금전적** 요소를 검토하며 우리 가족이 반려견의 보호자가 될 준비가 되었는지 점검할 수 있는 교육용 애플리케이션입니다.

</div>

### Introduction
> 2022 ICT멘토링 한이음 공모전 🏅입선🏅 수상 (한국정보산업연합회상) <br/>
> 개발 기간: 2022.03~2022.10

### Repository
> Client: https://github.com/SHAPE-UP/Bangida-client <br />
> Node.js server: https://github.com/SHAPE-UP/Bangida-server <br/>
> Flask server: https://github.com/SHAPE-UP/Bangida-voice <br/>


## Collaborators
### Team. SHAPE-UP
<img src="https://github.com/SHAPE-UP/.github/assets/79989242/a8982dd8-ff65-4a76-a438-3e66c8bb664d" width="200px" alt="team logo"> <br />

| <img src="https://avatars.githubusercontent.com/u/88462774?v=4" width=110px alt="류영주"/> | <img src="https://avatars.githubusercontent.com/u/79989242?v=4" width=110px alt="유수연"/> | <img src="https://avatars.githubusercontent.com/u/84445176?v=4" width=110px alt="김혜수"/> | <img src="https://avatars.githubusercontent.com/u/89893533?v=4" width=110px alt="황성민"/> |
| :-----: | :-----: | :-----: | :-----: |
| 류영주 | 김혜수 | 유수연 | 황성민 |
| [@yellow-jam](https://github.com/yellow-jam) |[@Hwater00](https://github.com/Hwater00)| [@otcroz](https://github.com/otcroz) | [@hwangsungmin-00](https://github.com/hwangsungmin-00)  |
| FE, BE, Infra | FE, BE, AI | FE, BE, AI | FE, BE, AI | 

<br/>


## Preview
https://github.com/SHAPE-UP/Bangida-client/assets/88462774/73c5ae44-c09c-4445-8668-8c556a00f2b0


<br/>

## Features
- 가족 단위 회원 관리
  - 가족 n명, 강아지 1마리를 전제로 한 그룹 단위 체험 관리
- To-Do 리스트
  - 시뮬레이션을 주기적으로 수행하도록 돕는 가이드
- 준비 정도 점검
  - 가족 그룹이 강아지를 맞이할 준비가 되었는지 진행도, 성실도, 호감도의 척도로 확인
- 반려견 예산서
  - 가족 단위 관리
  - 지출 내역, 비용, 카테고리, 구매주기 작성 후 기간별 지출 규모 확인  
- 반려견 관련 FAQ
- 반려견 시뮬레이션: 단계적으로 진행되는 터치 애니메이션 형식의 시뮬레이션
  - 시뮬레이션 활동과 관련된 훈련영상 Youtube와 연결하여 시청각 자료 제공 
1. 강아지 관리: 돌보기, 놀아주기, 장난감 세척
2. 먹이 주기: 하루에 제한된 양만큼의 사료, 물, 간식을 주는 과정 체험
3. 위생 관리/목욕: 빗질, 양치질, 목욕, 발톱 깎기/발바닥 관리, 귀 청소
4. 배변 관리/훈련: 배변패드 위치로 유도하는 적응훈련, 배변 상태에 따른 건강 상태 학습
5. 건강 관리: 시뮬레이션 내역을 바탕으로 집계된 반려견의 건강 상태 통계
6. 훈련하기: 사용자가 음성 훈련 어조를 연습할 수 있도록 지도 (음성의 빠르기, 크기 판단)
7. 산책하기: 반려견 동반 가능 시설 & 공공시설 지도 확인, 반려견 산책로 사전 조사(산책 경로, 평균 속도, 이동 시간, 주의사항 기록)
8. 병원, 미용: 반려견 관리 팁 영상

<br/>

## Service Architecture
![image](https://github.com/SHAPE-UP/Bangida-client/assets/88462774/31646a70-1a52-4e36-b635-7be99cb1a872)

> Client: <img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white"/>
> <img src="https://img.shields.io/badge/Kotlin-B125EA?style=flat-square&logo=Kotlin&logoColor=white"/> <br/>
> Server: <img src="https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=Node.js&logoColor=white"/>
> <img src="https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=MongoDB&logoColor=white"/> <br/>
> AI-Server: <img src="https://img.shields.io/badge/Flask-000000?style=flat-square&logo=flask&logoColor=white"/> <br/>
> Deploy: <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=flat-square&logo=Amazon EC2&logoColor=white"/>
