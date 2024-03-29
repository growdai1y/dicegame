# Dice Game OOAD

## 실습 양식

DiceGame_Artifacts.xlsx

## (읽을 거리) 단위 테스트 이면의 결함

강의중 언급한 숙련된 개발자는 서술 능력이 뛰어나다라는 부분을 알 게된 글이라 소개합니다.

<details>
<summary>The Theory behind Unit Testing by Michael Feathers.</summary>

https://michaelfeathers.typepad.com/michael_feathers_blog/2008/06/the-flawed-theo.html


> Quality is a function of precise thought and reflection. 품질은 정확한 사고와 성찰의 결과물입니다.


저는 단위 테스트에 대한 알림을 받도록 Google의 블로그 검색을 설정해 두었습니다.  평균적으로 일주일에 수십 개의 블로그와 메일링 리스트에서 해당 주제에 대한 토론을 읽습니다. 가끔 새로운 내용을 읽을 때도 있지만, 대부분 반복되는 내용이 많습니다.  같은 주장이 자주 등장합니다.   그중에서도 테스트와 품질에 대한 잘못된 이론에 근거한 유닛 테스트에 대한 주장은 저를 정말 괴롭히는데, 안타깝게도 제가 오래 전에 빠져들었던 주장이기 때문에 이제 그만 정리하고 싶습니다.  이 블로그가 도움이 되길 바라지만, 먼저 약간의 역사를 이야기해야겠습니다.

2000년대 초반에 한 컨퍼런스에서 스티브 프리먼과 대화를 나눈 적이 있습니다.  우리는 테스트 주도 개발에 대해 이야기하고 있었는데, 스티브는 당시 TDD를 실행하고 있던 대부분의 사람들이 뭔가 잘못하고 있다는 강한 느낌을 받았습니다.
스티브는 런던의 긴밀한 커뮤니티에 속해 있었으며, 초기부터 XP와 TDD를 실천해 왔습니다.   이들의 노력의 결실 중에는 모의 객체라는 개념이 있었습니다.  스티브 프리먼과 팀 맥키넌은 이 아이디어를 더 넓은 커뮤니티에 소개하는 논문을 썼습니다.  나머지는 역사입니다.  현재 거의 모든 언어에 대한 모의 객체 프레임워크가 널리 사용되고 있습니다.

그러나 모의 객체는 상대적으로 널리 알려지지 않은 더 큰 TDD 접근 방식의 일부입니다.  제가 들은 바로는 이 모든 것이 Connextra라는 스타트업의 CTO인 John Nolan에 의해 시작되었다는 이야기입니다. 존 놀란은 개발자들에게 게터 없이 OO 코드를 작성하라는 과제를 주었습니다.  가능하면 다른 객체에 요청하지 말고 무언가를 하라고 지시하세요.  이 과정에서 개발자들은 코드가 유연해지고 변경하기 쉬워진다는 사실을 발견했습니다.  또한 이들은 자신들이 작성하는 가짜 객체가 매우 반복적이라는 사실을 발견하고 객체에 대한 기대치를 설정할 수 있는 모킹 프레임워크, 즉 모의 객체에 대한 아이디어를 떠올렸습니다.

Steve가 이 접근 방식에 대해 이야기했을 때 괜찮은 아이디어라고 생각했지만 한 가지 이해가 되지 않는 점이 있었는데, Steve와 팀, 그리고 그 팀에 있던 사람들이 모크를 광범위하게 사용하고 있었다는 점이었습니다.  사실 그들은 할 수 있을 때마다 모형을 사용했습니다.  제가 TDD를 연습하는 방식과는 조금 달랐습니다.  저는 일반적으로 테스트를 통해 클래스를 구동한 다음, 설계 중인 클래스가 부피가 커지면 새로운 클래스를 추출했습니다.  어떤 테스트는 하나의 클래스만 다루지만 다른 테스트는 함께 작동하는 여러 클래스를 다루기도 했습니다.

모의 객체 접근 방식에서 제가 발견한 문제점은 개별 클래스만 테스트하고 클래스 간의 상호작용은 테스트하지 않는다는 것이었습니다.   물론 제가 작성한 테스트는 명목상 단위 테스트였지만, 가끔씩 클래스와 즉각적인 공동 작업자 간의 실제 상호 작용을 테스트한다는 점이 마음에 들었습니다. 물론 격리 테스트도 좋았지만, 통합 수준 테스트에 조금만 더 신경을 쓰면 테스트에 조금 더 힘이 실린다고 느꼈습니다.  하지만 한 가지 문제가 있었습니다.  모의 테스트를 광범위하게 사용하던 Connextra의 팀은 매우 낮은 결함률을 보고하고 있었습니다.  어떻게 그런 결과가 나왔는지 알 수 없었습니다.  결국 통합 테스트가 전혀 이루어지지 않은 것 같았습니다.  그들의 애플리케이션은 통합 오류로 가득 차 있었어야 했습니다.  아니면 정말 그랬을까요?  우리의 추론을 살펴봅시다.

단위 테스트에 대한 매우 일반적인 이론 중 하나는 테스트에서 발견되는 오류를 제거하면 품질이 향상된다는 것입니다.   표면적으로는 일리가 있는 말입니다.  테스트는 통과하거나 실패할 수 있고, 실패하면 문제가 있다는 것을 알게 되어 수정할 수 있기 때문입니다.  이 이론을 따르면 통합 테스트를 수행할 때 통합 오류가 줄어들고 단위 테스트를 수행할 때 "단위" 오류가 줄어들 것으로 기대할 수 있습니다.  좋은 이론이지만 잘못된 이론입니다.  이를 확인하는 가장 좋은 방법은 단위 테스트를 매우 극적인 측정 가능한 효과가 있는 다른 품질 개선 방법과 비교하는 것입니다.

1980년대에 클린룸 소프트웨어 개발이라는 것을 사용하려는 움직임이 있었습니다.  클린룸의 기본 개념은 개발의 엄격성을 높여 품질을 높일 수 있다는 것이었습니다.  클린룸에서는 코드의 모든 작은 부분에 대해 논리적 술어를 작성해야 했고, 검토 과정에서 코드가 설명된 술어보다 더하거나 덜 수행하지 않는다는 것을 입증해야 했습니다.  이는 매우 진지한 접근 방식이었으며 방금 설명한 것보다 조금 더 급진적이었습니다. Clean Room의 또 다른 신조는 단위 테스트가 없어야 한다는 것이었습니다.  전혀.  코드를 작성하면 검토를 거친 후 올바른 것으로 간주했습니다.  수행된 유일한 테스트는 기능 수준에서의 확률적 테스트였습니다.  

놀랍게도 클린룸은 효과가 있었습니다.  클린룸 팀은 매우 높은 품질의 수치를 보여주었습니다.  이 소식을 접하고 깜짝 놀랐지만, 그러던 중 프로세스에 대해 읽던 책에서 한 구절을 발견했습니다. 저자는 많은 프로그래머가 코드 블록을 작성한 후 술어를 작성하지만, 숙련된 프로그래머는 종종  술어[[1]](#link1)를 먼저 작성한다고 말했습니다.  익숙한 이야기 같지 않나요?  TDD에서는 테스트를 먼저 작성하며, 테스트는 본질적으로 작성하려는 코드의 동작에 대한 명세서입니다.

소프트웨어 업계에서는 수년 동안 품질을 추구해 왔습니다.  흥미로운 점은 효과가 있는 여러 가지 방법이 있다는 것입니다.  계약에 의한 설계는 효과가 있습니다.  테스트 주도 개발도 효과가 있습니다.  클린룸, 코드 검사, 상위 수준 언어 사용도 마찬가지입니다.

이 모든 기법들은 품질을 향상시키는 것으로 나타났습니다.  그리고 자세히 살펴보면 그 이유를 알 수 있습니다. 이 모든 기법들이 코드를 되돌아보게 하기 때문입니다.
이것이 바로 단위 테스트의 마법이며, 단위 테스트가 작동하는 이유이기도 합니다.  TDD 스타일로 단위 테스트를 작성할 때나 개발이 끝난 후에도 면밀히 검토하고 생각하면 테스트 실패를 경험하지 않고도 문제를 예방할 수 있는 경우가 많습니다.

이 글을 읽으면서 의자에 앉아서 턱을 괴고 코드에 대해 생각만 하면 아무것도 하지 않아도 된다고 생각할 수도 있습니다.  저는 그렇게 생각하지 않습니다.  그런 접근 방식이 일부 사람들에게는 단기간에 효과가 있을 수 있지만 소프트웨어 개발은 장기적인 활동입니다.  우리는 지속적인 규율과 지속적인 성찰 상태를 달성하는 데 도움이 되는 관행이 필요합니다.  클린룸과 TDD는 근본적인 차이에도 불구하고 우리가 하고 있는 일에 대해 절대적으로 정확하게 생각하도록 만드는 두 가지 관행입니다.
클린룸을 사용하는 팀도 충분히 잘할 수 있겠지만, 개인적으로 저는 TDD를 사용하면 코드 베이스 전체를 다시 추론할 필요 없이 코드를 쉽게 변경하고 여전히 작동하는지 확인할 수 있다는 점에서 추가적인 활용도가 있다는 점이 마음에 듭니다.   특히 추론을 기록하고 구현한 테스트를 작성하여 마음대로 실행할 수 있는데도 코드를 자주 변경해야 한다면 이는 시간을 낭비하는 일입니다.  이러한 테스트가 준비되어 있으면 끝없이 반복하는 대신 과거에 추론하지 못했던 다른 것에 대해 자유롭게 추론할 수 있습니다.

하지만 TDD에 대한 이야기는 충분합니다.

제 요점은 테스트를 기계적으로 바라볼 수 없다는 것입니다.  단위 테스트는 단위 수준에서 오류를 잡아내는 것 만으로 품질이 향상되지 않습니다.  그리고 통합 테스트도 통합 수준에서 오류를 잡아낸다고 해서 품질이 향상되는 것은 아닙니다.  진실은 그보다 더 미묘합니다.  품질은 사고와 성찰, 즉 정확한 사고와 성찰의 함수입니다.  이것이 바로 마법입니다.  이러한 규율을 강화하는 기술은 언제나 품질을 향상시킵니다.


<a name="link1">[1]</a> '서술어'라고도 하며 문장 구성의 기본 골격이 되는 요소로서, 주어의 동작·상태·성질 따위를 서술하는 말을 가리킨다.
</details>

