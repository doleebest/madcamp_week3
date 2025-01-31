# 🍅 냉장고를 부탁해
<div align="center">
<h2> 냉장고를 부탁해! 는 자취생을 위한 냉장고 및 식단 관리 앱입니다 🍎🍊🍖🥦🍴 </h2>
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

 <table>
    <tr>
      <td align="center">메인화면</td>
      <td align="center">기록하기</td>
    </tr>
    <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/ba7da632-293e-4e7c-9d56-7cb29ce4b7a9" width="500" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/537cb8b4-d423-42f5-9c2d-c80ca9f441c7" width="500"/></td>
    </tr>
    <tr>
      <td align="center">추억하기</td>
      <td align="center">대화하기</td>
    </tr>
    <tr>
      <td align="center"><img src="https://github.com/user-attachments/assets/4a63e1f7-07b2-48fa-83e4-723fa8eea15e" width="500" /></td>
      <td align="center"><img src="https://github.com/user-attachments/assets/6aea33d2-2c1d-4b01-8b7d-ba03e9d9fab3" width="500" /></td>
    </tr>
 </table>


<br>

### 메인화면 및 구글 소셜 로그인 기능
- 웹을 처음 실행하면 화면이 표시됩니다. (`tailwind`로 구름이 둥둥 떠다녀요~ ☁️)
- 그리고 로그인하기 혹은 구글 계정으로 시작하기를 누르면 구글 소셜 로그인 페이지로 redirect 됩니다.
- - login을 하면 `mysql database`에 회원 데이터가 삽입됩니다.
- 상단 네비게이션 바를 누르면 구글 회원 데이터를 불러옵니다.  
<img width="443" alt="Image" src="https://github.com/user-attachments/assets/7408982b-8ef4-4c7c-8321-8c9f9769b13f" /><br>
- 물론 로그아웃도 가능합니다! (다시 처음 화면으로 돌아갑니다.) <br>

### 기록하기

- 반려동물의 정보를 기록할 수 있는 탭입니다. 최대한 정보를 자세하게 입력하는 것이 좋습니다. 내 반려 동물의 성격, 장점, 단점, 특징, 행복했던 추억, 사고쳤던 기억 등을 기록을 하면서 내가 기르던 반려동물에 대해 상기해보는 효과도 있습니다. 🌿
- 수정하고 싶은 마음이 들어 ‘이전으로’를 눌러 돌아가면 해당 데이터들이 남아 있고, 그리고 입력하지 않은 채로 ‘다음으로’를 누르면 경고문이 뜹니다.

### 추억하기

- 반려 동물과의 추억을 사진으로 올릴 수 있는 페이지입니다. 사진과 함께 제목, 내용을 입력하면 업로드가 됩니다.  
<img width="500" alt="Image" src="https://github.com/user-attachments/assets/d217b8af-2794-4fa7-8967-2bb1d6b2d12c" /><br>
- 그리고 갤러리 기능을 추가하여`masonry view`로 반려동물과의 추억을 모아볼 수 있게 uxui를 구성했습니다. 스크롤도 가능합니다.

### 대화하기
- 내가 추가했던 동물들의 목록이 나오고 그 중에서 동물을 선택하여 대화를 나눌 수 있습니다. <br>
<img width="763" alt="Image" src="https://github.com/user-attachments/assets/765b70d0-7b6d-4e29-bca0-fc7bd0555179" width="500"/><br>
- 이때, `ChatGPT API`를 따왔는데, 이전에 사용자가 “기록하기”에서 입력했던 정보들과 대화 히스토리들이 모두 ChatGPT에 프롬프트로 들어가게 됩니다. 사용자는 세상을 떠난 반려동물이 된 ChatGPT와 대화를 나누며 반려동물이 언제나 곁에 있는 기분을 느낄 수 있습니다 ✨ `(프롬프트 엔지니어링)`
- 더불어, 추억하기 탭에서 사진과 함께 올렸던 content도 ChatGPT에 적용이 됩니다.
- 그리고 이 목록에서 반려동물 정보를 삭제하거나, 수정할 수 있습니다. 목록에서 반려동물을 누르면 “기록하기”탭으로 돌아가서 정보를 수정할 수 있습니다. 예를들어 새로운 추억이 더 생각났다면 기록할 수 있겠죠?
- 대화 히스토리는 저장되어 언제든 다시 들어가서 대화를 나누고 싶을 때마다 다시 대화를 시작할 수 있습니다.


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
