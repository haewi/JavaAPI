# JAVA API for CONNSIX

## About
이 API는 자바 프로그래밍 언어로 개발한 육목 AI를 프로그램들을 위한 API입니다. 모든 method들은 싱글 모드 서버와 연결하고 소통하기 위한 기능들이 구현되어있습니다.

## Methods
* public ConnectSix(String ip, int port, String color)
* public String letsConnect(String ip, int port, String color)
* public String drawAndRead(String draw)
* public String query(String position)
<br>
<b>각 method에 대한 구체적인 설명은 javadoc을 참조하시기 바랍니다.</b>

## 좌표 시스템
바둑판의 좌표는 alphabet character 하나와 숫자로 표기합니다. 바둑판의 세로줄은 alphabet 'A' ~ 'T' 로 표기하고 이때 'I'는 포함되지 않습니다. 가로줄은 숫자 1 ~ 19로 표기하며 10 이하의 숫자들은 십의 자리에 0을 넣어주어야합니다. 하나의 좌표는 이 두 정보를 합친 String으로 표현해야합니다.
* 예시) "A01", "B03", J12"
여러 좌표들을 표기하기 위해서는 좌표들 사이에 ":"를 넣어 하나의 String으로 만들어야합니다.
* 예시) "A01:E13", "E11:J18:K10:T19"
<br>
밑의 이미지는 앞서 설명한 좌표 시스템을 나타낸 것입니다.
<br>
<img src="./image/coordinate_system.png" alt="coordinate system" width="500"/>

## Dummy AI
'dummyAi'라는 폴더 안에는 이 API를 사용하는 예시 프로그램이 있습니다. 이 프로그램은 무작위로 두개의 좌표를 생성해내 API내의 method들을 이용해 싱글 모드 서버에 보냅니다. 이때 무작위로 생성하는 두 좌표는 유효한 좌표인지를 확인하지 않기 때문에 유효하지 않은 좌표로 인해 싱글 모드 서버에서 게임을 종료할 수 있습니다.
