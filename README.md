Image Processing Living Intelligence_Android
------
This is a repository for Android module of Image Processing Living Intelligence.<br/>

This project requires a **subnet** configuration between the server, the hardware and the Android module.<br/>


Requirements
------
1. 프로젝트 로컬에 다운로드 or 클론<br/>
[https://github.com/IPLI/IPLI_Android](https://github.com/IPLI/IPLI_Android)<br/><br/>

2. Android Studio 설치<br/>
Android Studio 설치 관련 url : 
[https://ohjinjin.github.io/android%20studio/install-androidstudio/](https://ohjinjin.github.io/android%20studio/install-androidstudio/_)<br/><br/>

3. APM 설치<br/>
APM 설치 관련 포스팅 url : 
[https://ohjinjin.github.io/database/apmsetup/](https://ohjinjin.github.io/database/apmsetup/)<br/><br/>
APM MySQL 한글 인코딩 관련 포스팅 url :
[https://ohjinjin.github.io/database/mysql-encoding/](https://ohjinjin.github.io/database/mysql-encoding/)<br/><br/>
DB 다이어그램 : 
<!--<img src="https://github.com/IPLI/IPLI_Android/db.tif" alt="">-->
![db](./db.png)<br/><br/>

4. DB migrations<br/>
DB 다이어그램대로 데이터베이스 및 테이블 설계를 해주세요.<br/>
또한 상품의 경우에는 다섯개를 추가해줘야합니다.<br/>
상품은 코카콜라, 다우니, 버터링, 콘푸로스트, 라면입니다.<br/>

상품 이미지는 모두 함께 이 원격 레포지토리에 올려뒀습니다.<br/>
butter.png, coca.png, confu.png, daw.png, shin.png가 그것들입니다.<br/><br/>

DB에 데이터 추가하실 때에는 아래처럼 넣어주시면됩니다.<br/>
<!--<img src="https://github.com/IPLI/IPLI_Android/DBinfo.PNG" alt=""><br/><br/>-->
![DBinfo](./DBinfo.png)<br/><br/>
단 이미지 url의 경로는 여러분의 DB서버 ip로 바꿔주세요.<br/>

로그인 계정은 어플 실행 후 생성해서 사용하면 됩니다.<br/> 

php도 함께 올려드렸는데 htdocs 폴더안에 이미지와 php를 모두 넣어주시면됩니다.<br/>

안드로이드 어플리케이션 자바코드에서 서버컴퓨터의 ip로 고쳐주시면 정상 실행됩니다.<br/><br/><br/>


Usage
------
시연 영상 : [https://youtu.be/Cik78MP8W3E](https://youtu.be/Cik78MP8W3E)
<br/>
관련 포스팅 : [https://ohjinjin.github.io/projects/IPLI/](https://ohjinjin.github.io/projects/IPLI/)
<br/>
