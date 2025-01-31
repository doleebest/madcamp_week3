# 🍅 냉장고를 부탁해
<div align="center">
<h2> 냉장고를 부탁해! 는 자취생을 위한 AI 냉장고 및 식단 관리 앱입니다 🍎🍊🍖🥦🍴 </h2>
<p>
자취생들은 아무래도 냉장고에 음식들이 많으면 (특히 부모님이 가져다 주신 음식) 관리하기가 힘들고 또 균형잡힌 식사를 챙겨 먹기가 힘듭니다.
➡️ 그래서 자취생들이 냉장고의 식재료를 잘 관리하고, 그 식재료로 균형잡힌 식사를 해먹을 수 있도록 도와주며,
불건강한 음식들을 많이 살 경우 부모님에게 알림 문자가 가도록 하는 서비스를 구현해보게 되었습니다.
<br> <br>
</p>
    
</div>
<br />

## 🧑‍💻 팀원 소개

  <table>
    <tr>
      <td align="center"><img src="https://github.com/younjihoon.png" width="160"></td>
      <td align="center"><img src="https://github.com/doleebest.png" width="160"></td>
    </tr>
    <tr>
      <td align="center">윤지훈</td>
      <td align="center">이소정</td>
    </tr>
    <tr>
      <td align="center"><a href="https://github.com/younjihoon" target="_blank">@younjihoon</a></td>
      <td align="center"><a href="https://github.com/doleebest" target="_blank" width="160">@doleebest</a></td>
    </tr>
    <tr>
      <td align="center">카이스트 전기전자공학부 20학번</a></td>
      <td align="center">이화여대 컴퓨터공학과 21학번</a></td>
    </tr>
      <tr>
      <td align="center">Android, AI</a></td>
      <td align="center">BE, CSS, AI</a></td>
    </tr>
    </tr>
  </table>
  <br>


## 🐶 프로젝트 소개


'냉장고를 부탁해'에서는 세 가지 탭을 사용해볼 수 있어요.

 
> 1. **냉장고 채우기**<br>
장을 보거나 물건을 구매하고 나서 그 목록을 냉장고에 업데이트 해보세요.
> 2. **정보 입력하기**  
여러분의 식사를 걱정해주는 누군가 (ex. 부모님, 연인, 친구 등)의 연락처를 적어둡니다. (왜 인지는 다음 섹션에서 TBC...)
> 3. **냉장고 살펴보기**  
내 냉장고의 식재료를 Edamam api가 분석해, 점수를 매겨줍니다. 이 점수가 60점 이하로 내려가면 이전 섹션에서 입력한 사람에게 문자가 전송됩니다! 건강한 식사를 할 수 있도록 주의를 기울여주세요 🚨
> 4. **냉장고 비우기**
내 냉장고에 있는 식재료를 이용한 음식을 gemini가 파악하여, 적당한 음식을 추천해 줍니다.

<br>

## 🛠 주요 기능

<style>
  .grid-2x2 {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 10px;
    justify-content: center;
  }
  
  .grid-1x2 {
    display: flex;
    justify-content: center;
    gap: 10px;
  }
</style>

<table>
    <tr>
        <td align="center">메인화면</td>
        <td align="center">기록하기</td>
    </tr>
    <tr>
        <td align="center" class="grid-1x2">
            <img src="https://github.com/user-attachments/assets/27ebe94b-d610-48b0-bc13-71472dfb106b" width="500" />
            <img src="https://github.com/user-attachments/assets/2a8ff57d-e2df-492a-b53f-58dbdeed20ea" width="500" />
        </td>
        <td align="center" class="grid-2x2">
            <img src="https://github.com/user-attachments/assets/ae9d7c7c-c6c5-48fd-9b33-25158a918742" width="500" />
            <img src="https://github.com/user-attachments/assets/52144df8-aa51-4e17-9203-955652c63604" width="500" />
            <img src="https://github.com/user-attachments/assets/0d3c1d36-2926-4ef6-9464-e9674c6bbcd2" width="500" />
            <img src="https://github.com/user-attachments/assets/670e313d-b9c5-4d8a-8b42-634790cde808" width="500" />
        </td>
    </tr>
    <tr>
        <td align="center">추억하기</td>
        <td align="center">대화하기</td>
    </tr>
    <tr>
        <td align="center">
            <img src="https://github.com/user-attachments/assets/84c49ce6-1fd7-430d-a61a-18fbbfe7719a" width="500" />
        </td>
        <td align="center" class="grid-2x2">
            <img src="https://github.com/user-attachments/assets/24b3926f-9f8a-4948-9bce-01815fe691f4" width="500" />
            <img src="https://github.com/user-attachments/assets/dd786376-62b2-4304-b752-1c5f8a72a658" width="500" />
            <img src="https://github.com/user-attachments/assets/30cef6a1-0f9a-4f10-8ae1-4c148c99f668" width="500" />
            <img src="https://github.com/user-attachments/assets/8882b990-6a28-431a-8f94-aad6f87a6963" width="500" />
        </td>
    </tr>
</table>




<br>

### 메인화면 및 구글 소셜 로그인 기능
- 웹을 처음 실행하면 냉장고가 열리는 화면이 표시됩니다.
- 그리고 로그인하기 혹은 구글 계정으로 시작하기를 누르면 구글 소셜 로그인 페이지로 redirect 됩니다
- login을 하면 `firebase database`에 회원 데이터가 삽입됩니다.
- “냉장고 살펴보기”를 누르면 구글 회원 데이터를 불러옵니다.
    - 이름, 이메일, 프로필 사진
- 물론 로그아웃과 회원 탈퇴도 가능합니다! (다시 처음 화면으로 돌아갑니다.)<br>

### 냉장고 채우기

- 영수증을 찍으면, gemini가 냉장고에 넣을 만한 식품들을 알아서 인식해 식품 목록에 업데이트를 해줍니다.
- 이때 가공식품과 신선식품의 사진이 다르게 입력됩니다.
- 또한 장을 봐온 물건들을 카메라로 찍으면, google vision api가 이미지 인식, 혹은 텍스트 인식으로 식품 목록에 업데이트를 해줍니다.
- 만약 인식이 안된 물건이 있다면, 수동으로 물건을 추가할 수도 있

### 정보 입력하기

- 여러분의 식사를 걱정해주는 누군가 (ex. 부모님, 연인 등)의 연락처를 적어둡니다.
- 왜 적는지는 다음 섹션에서 설명드리겠습니다.

### 냉장고 살펴보기
- 내 냉장고의 식재료를 Edamam api가 분석해, 점수를 매겨줍니다.
    - 예를들어, 채소 및 단백질류가 많으면 점수가 올라가고 가공식품(캔, 과자 등)이 많으면 점수가 내려가도록 카테고리화했습니다.
- 그리고 이 점수가 60점 이하로 내려가면 이전 섹션에서 입력한 사람에게 문자가 전송됩니다! 건강한 식사를 할 수 있도록 주의하세요 🚨

### 냉장고 비우기
- 냉장고 비우기 메뉴를 누르면 스플래쉬 화면이 움직이고,
- 내 냉장고에 있는 식재료를 이용한 음식을 gemini가 파악하여, 적당한 음식을 추천해 줍니다.
    - 예를 들어, 닭고기가 있으면 닭볶음탕, 닭구이, 닭가슴살덮밥 등을 추천합니다.
    - 필요한 재료와 이미 가지고 있는 재료를 구분해서 볼 수 있습니다.


<br />

## 📖 개발 환경  

- **Front-end** : Kotlin

- **IDE** : Android studio

- **Back-end** : spring, spring boot, java, mysql, firebase, postman, AWS

- **IDE** : intellij

- **AI** : Python, Google Vision API, Gemini

- **IDE** : VS code

<br>

## 🕰️ 개발 기간  
2025.01.09 ~ 2025.01.15


<br />

## 💬 개발 소감
### 이소정

이번에는 배포에 익숙해지는 것이 목표였는데, 그 목표를 달성할 수 있어서 정말 좋았습니다! 배포와 더불어 로그인까지도 잘 해냈고, 코드 한 줄 한 줄이 잘 이해가서 신기하고 뿌듯한 주차였습니다. 그리고 지훈이가 개발 경험이 있고 구글 api와 gemini를 잘 써준 덕분에 db 관리도 잘 되어서 고맙다는 말을 전하고 싶습니다! 🎀

### 윤지훈

애니메이션을 만들어 보며 프론트에 재미를 느끼게 되었습니다. 하루종일 코드 짜면서도 즐겁게 놀아준 소정 누나에게 감사의 말을 전합니다!
