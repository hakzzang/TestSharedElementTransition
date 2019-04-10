### 0. 서론

플루터의 엄청난 애니메이션이 너무 부러웠다.
그래서 안드로이드의 Shared Element Transtions에 대해서 공부를 하기 시작한다.

정말 좋은 글을 정독하는 것이 이번 블로그의 목표이다.

https://mikescamell.com/shared-element-transitions-part-1/
https://mikescamell.com/shared-element-transitions-part-2/

Shared Element는 오직 21위에서만 가능하다고 한다. 21버전 이전에서는 뷰와 뷰 사이에 기본적인 이동으로만 작용한다. 그리고, 제대로 구현만 됐다면 런타임 에러 또한 나지 않는다.

작업방법

-      액티비티 A에서 이미지가 있다. 너가 누른다면 액티비티 B로 이동한다.
-      액티비티 B는 스크린에서 이미지를 로딩한다.
-      이 프레임워크는 몇몇 계산과 시작과 끝이 어디에서 하는지, 요구되는 사항에 대해서 작용한다.
-      애니메이터를 만들어서 이동하려는 하는 뷰를 다룬다.
-      이 프레임워크는 Activity A에서 Element가 사라지고, 공유된 속성값의 마지막 값이 ActivityB에서 애니메이팅 된다.

공유된 속성 전환값은 Window의 ViewOverlay에 위치한다. 이 오버레이는 다른 모든 뷰의 위에 있다.

1. Activity에서 Activity
우선 windowContentTranstions가 가능해야한다. Style을 v-21 이상에서 만드는 것을 추천한다. 그리고 style에 해당 값을 추가해준다.
<pre>
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>

        <!-- 해당 속성 값을 통해서 Shared Element Transtion 가능-->
        <item name="android:windowContentTransitions">true</item>
</style>
</pre>

#### 1-1. Activity A -> Activity B, Activity B-> Activity A

 - 우선 Activity A에서 Activity B로 옮기기 위해서 setOnClickListener를 만들어준다. 

 - Activity를 이동하기 위해서 일반적인 intent를 만들어서 Activity B로 이동하게 해준다.

 - ActivityOptionsCompat을 통해서 makeSceneTranstionAnimation을 만들어준다.

 - 똑같이 Activity B에 해당 option과 intent를 통해서 Activity A로 이동하는 로직을 만듭니다.

#### 1-2. Activity A UI

~~~
<pre>
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
 
    <ImageView
            android:id="@+id/iv_android_icon"
            android:layout_width="128dp"
            android:layout_height="128dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:src="@mipmap/ic_launcher"
            android:transitionName="icon_android"/>
 
</android.support.constraint.ConstraintLayout>
</pre>
~~~


#### 1-3. Activity B UI

~~~
<pre>
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">
 
    <ImageView
            android:id="@+id/iv_android_icon"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:src="@mipmap/ic_launcher"
            android:transitionName="icon_android"/>
 
</android.support.constraint.ConstraintLayout>
</pre>
~~~
#### 1-4 Activity A

<pre>
class AActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        iv_android_icon.setOnClickListener {
            val intent = Intent(this, BActivity::class.java)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    this@AActivity, iv_android_icon,
                    ViewCompat.getTransitionName(iv_android_icon)!!
                )
            startActivity(intent, options.toBundle())
        }
    }
}
</pre>

#### 1-5. Activity B
<pre>

class BActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)
        iv_android_icon.setOnClickListener {
            val intent = Intent(this, AActivity::class.java)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    this@BActivity, iv_android_icon,
                    ViewCompat.getTransitionName(iv_android_icon)!!
                )
            startActivity(intent, options.toBundle())
        }
    }
}
</pre>

AActivity에서 사용하는 코드는 위와 같고, 일반적인 애니메이션과 함께 intent를 통해서 액티비티를 이동시키는 로직과 유사하게 느껴졌습니다. 특징은 ActivityOptionsCompat이 사용되었다는 점입니다.
그래서, ActivityOptionsCompat이라는 객체를 처음봐서 들어가봤습니다.

<pre>
public class ActivityOptionsCompat {
    @NonNull
    public static ActivityOptionsCompat makeCustomAnimation(){}
 
     @NonNull
    public static ActivityOptionsCompat makeScaleUpAnimation(){}
 
    @NonNull
    public static ActivityOptionsCompat makeClipRevealAnimation(){}
 
    @NonNull
    public static ActivityOptionsCompat makeThumbnailScaleUpAnimation(){}
 
    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(){} 
 
    @NonNull
    public static ActivityOptionsCompat makeSceneTransitionAnimation(){}
 
    @NonNull
    public static ActivityOptionsCompat makeTaskLaunchBehind() {}
 
    @NonNull
    public static ActivityOptionsCompat makeBasic() {}
 
    //...
}
</pre>

다양한 make 함수들이 있다는 것을 알게 됐고, 공부해야할게 이만큼이나 있다는 것을 알게 되었습니다. 저희는 그 중에서도, makeSceneTranstionAnimation을 사용하는데, 씬이동애니메이션을 만드는 함수를 통해서 이동시킨다는 것을 알 수 있었습니다.
다시, 로직을 살펴보면, makeSceneTranstionAnimation 매소드에는 content와 이동시킬 view, unique한 transtion 이름이 필요하다는 것을 알 수 있었습니다. 해당 로직을 통해서 얻은 결과물은 아래와 같습니다.
해당 결과물을 통해서 액티비티 이동시에, 사용하던 값을 갖고 다른 액티비티로 이동하는 모션을 만들 수 있을 것으로 기대합니다.

#### 1-6. 결과물

![SET1](https://user-images.githubusercontent.com/22374750/55861973-76fb5580-5bb2-11e9-8589-843484c5873a.gif)

### 2. Fragment 에서 Fragment

Shared Element Transtions를 사용하기 위해서는 아래와 같은 것을 지켜줘야 한다. 해당 url은 https://material.io/design/motion/#material-motion-what-makes-a-good-transition 이다.

-   우리는 사용자에게 감탄스러운 애니메이션을 제공해주는 동안 너무 빠르게 애니메이션이 끝나지 않게 방해하지 말아야한다. 만약 너무 빠르거나 느리게 할 경우에는 애니메이션을 통해 좋은 경험을 제공하지 못할 것이다.
-   너무 많이 한번에 Shared Element Transtions를 사용하면 안된다. 우리는 가능한 명확하게 의도를 원한다. 너무 많은 Shared Element Transtions으로 잠재적인 이동과 다른 방향으로 움직이는 것은 혼란스럽게 할 수 있다.
-   일관성을 유지해야 한다. 만약 너가 Shared Element Transtions을 사용한다면, 계속해서 애니메이션을 앱에서 사용하고, 유지시켜주지 않으면 사용자는 좋지 않은 경험을 얻을 것이다. 그것은 불편하게 하고, SET를 사용하지 않는 것보다 나쁜 경험을 제공할 것이다. 일관성 있게 애니메이션을 제공해줘서 사용자들이 그들이 무엇을 의미하는지 그리고 그들의 의도를 이해하는데 도움을 줘야한다.

#### 2-1. FFActivity

<pre>
class FFActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ff)
        title = "Fragment to Fragment"
        createAFragment()
    }
 
    private fun createAFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, AFragment.newInstance())
            .commit()
    }
}
</pre>

Fragment에서 Fragment로 이동하는 Transtion에 대한 코드이고, Activity에서는 AFragment를 만들어서 UI를 대기하고 있다.

#### 2-2. AFragment

<pre>
class AFragment : Fragment() {
    companion object {
        fun newInstance(): AFragment {
            val args: Bundle = Bundle()
            val fragment = AFragment()
            return fragment
        }
    }
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
 
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }
 
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_android_icon.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction()
                    .addSharedElement(iv_android_icon, ViewCompat.getTransitionName(iv_android_icon)!!)
                    .addToBackStack("icon_android")
                    .replace(R.id.fl_container, BFragment.newInstance())
                    .commit()
            }
        }
    }
}
</pre>

AFagment는 view가 만들어졌을 때, icon을 클릭할 경우에 fragmentManager를 호출하고, BFragment를 호출하는 로직이다.

#### 2-3. BFragment

<pre>
class BFragment : Fragment() {
    companion object {
        fun newInstance() : BFragment{
            val args : Bundle = Bundle()
            val fragment = BFragment ()
            return fragment
        }
    }
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }
 
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }
}
</pre>

BFragment는 view를 받고, 이미지를 그리는 것인데 여기에는 sharedElementEnterTranstion을 설정해준다. 저 부분에 대해서 추후에 공부를 더 해서 구체적으로 Custom해서 다루기로 하겠다.

#### 2-4. FFActivity xml

~~~
<pre>
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".FFActivity">
 
    <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:id="@+id/fl_container"
            app:layout_constraintBottom_toBottomOf="parent"/>
 
 
</android.support.constraint.ConstraintLayout>
</pre>
~~~

#### 2-5. Fragment A xml

~~~
<pre>
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".FFActivity">
 
    <ImageView
            android:id="@+id/iv_android_icon"
            android:layout_width="64dp"
            android:layout_height="64dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:transitionName="icon_android"
            android:src="@mipmap/ic_launcher"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintVertical_bias="0.5"/>
 
</android.support.constraint.ConstraintLayout>
</pre>
~~~

#### 2-6. Fragment B xml

~~~
<pre>
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".FFActivity">
 
    <ImageView
            android:id="@+id/iv_android_icon"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            android:transitionName="icon_android"
            android:src="@mipmap/ic_launcher"/>
 
</android.support.constraint.ConstraintLayout>
</pre>
~~~

#### 2-7. 결과물
![SET2](https://user-images.githubusercontent.com/22374750/55861976-782c8280-5bb2-11e9-8cc9-1df9cb3d56c7.gif)




﻿
