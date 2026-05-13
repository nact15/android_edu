## Arch

1. Clean architecture
2. MVP, MVVM, MVI, ELM, VIPER
3. SOLID, DRY, KISS
4. Полиморфизм, инкапсуляция, наследование, абстракция

## Git

1. git reflog
2. git worktree

## Kotlin

1. val, var, const val, lateinit
   const val должна быть top-level, либо внутри object / companion object.
   Почему не внутри функции: потому что const val должна быть известна компилятору как стабильная
   константа, доступная как часть класса/файла, а локальная переменная функции создается в момент
   вызова функции.
2. == vs ===
3. object (equals, hashcode, toString)
   #### object — самостоятельный singleton.
   #### companion object — singleton внутри класса, для вещей, связанных с этим классом.
4. data class vs class
   * data class автоматически генерирует equals() по свойствам из primary constructor.
   * data class генерирует componentN() автоматически по параметрам primary constructor.
   * class — обычный класс для поведения, lifecycle, сервисов, экранов, адаптеров.
   * data class — класс для данных.
5. sealed classes
6. Interface VS Abstract Class vs Sealed Class
7. high order function
   #### Higher-order function — функция, которая принимает функцию или возвращает функцию.
   it — неявное имя единственного параметра lambda.
8. inline functions
#### “вставь тело этой функции прямо в место вызова”
**в inline lambda можно сделать return из внешней функции.**

* crossinline запрещает опасный/невозможный non-local return. 
* noinline говорит: “эту lambda не встраивай, оставь как обычный объект”. 
* inline lambda -> код встраивается, нельзя сохранить как объект 
* noinline lambda -> остается объектом, можно сохранить/передать дальше
9. infix functions
* Быть member function или extension function. 
* Иметь ровно один параметр.
* Не иметь vararg. 
* Не иметь default value для параметра. 
* Быть помечена ключевым словом infix.
## 10. operator functions
* plus 
* minus
* times
* div 
* get 
* set 
* contains 
* invoke
* compareTo
## 11. value class
    - value class — отдельный тип поверх одного значения.
    - value class дает отдельный тип, который компилятор часто представляет как исходное значение
## 12. memory, object links, reference types, GC
**Int? должен уметь хранить null, а Java primitive int не может быть null.**
Поэтому используется объектная обертка.
    - stack — память для вызовов функций и локальных переменных.
    - heap — память для объектов.
    
#### Reachability: **достижимость**

Объект жив, если до него можно дойти по ссылкам из “корней”.
Корни — это, например:
- активные локальные переменные в текущих потоках
- static/object singletons
- живые Activity/Fragment/ViewModel
- системные ссылки
13. generics (in class, in methods, in, out, where, reified)
    * in = consumer = можно принимать T
    * out = producer = можно читать T
    * Producer Extends, Consumer Super(PECS)
    * Box<*> значит: “Box с каким-то неизвестным типом”(Any?)
    * reified буквально означает примерно “сделанный реальным”: тип T становится доступен внутри inline-функции.
## 14. рефлексия 
#### Рефлексия — способность программы узнавать информацию о классах/свойствах/функциях во время выполнения.
## 16. ::
    - :: — это оператор ссылки.
    - User::class // class reference
    - ::printName // function reference
    - User::name // property reference
    - ::User // constructor reference
    - ::title // property reference for lateinit check
## 16. let, run, apply, also, use
* let -> it -> результат lambda (null)
* run -> this -> результат lambda (вычисления)
* apply -> this -> исходный объект (.., настроить объект)
* also -> it -> исходный объект (side effect)
* use -> it -> результат lambda + закрывает ресурс (закрыть ресурс)
## 17. collections
    - map - Преобразуй каждый элемент.
    - filter - Оставь только подходящие.
    - flatMap - Каждый элемент в список, потом склеить.
    - groupBy - Сгруппировать по ключу.
    - associateBy - Сделать map: ключ -> элемент.
    - firstOrNull(find) - Первый подходящий или null.
    - any/all/none - Проверки по условию.
    - sortedBy - Отсортировать по полю.
    - distinctBy - Убрать дубликаты по ключу.
    - partition - Разделить на две группы: подходит / не подходит.
    - fold - Накопить одно значение.

**Sequence — lazy pipeline обработки элементов.**
Sequence = лениво + поэлементно + запускается terminal-операцией]
## 18. видимость
- public — доступно всем, по умолчанию.
- private внутри класса — только внутри класса.
- private top-level — только внутри файла.
- protected — внутри класса и наследников.
- internal — внутри Gradle/Kotlin module.
- private set — читать можно публично, менять только внутри класса.

## Async

1. coroutines + flow (flow, shared, state, channel)
2. rxJava3 + subjects (optional topic, chains)
3. Java util concurrent (optional topic)

## Android

## 1. Android components - Activity, Broadcast Receiver, Content Provider, Service.
   * Activity — экран/точка входа для UI.
   * Service — фоновая или долгоживущая работа без собственного UI.
   * BroadcastReceiver — реакция на системные или app-события.
   * ContentProvider — способ отдавать данные другим приложениям или частям системы.
   * Application — не компонент из той четверки, но важный глобальный объект приложения.
   * AndroidManifest.xml — это декларативное описание приложения для системы
## 2. Components lifecycles & callbacks (Application, activity, broadcast receiver, content provider.
   onCreate, onStart e.t.c. & callback
   diffs)
### BroadcastReceiver
`Application.onCreate()`
один раз на процесс, глобальная инициализация

#### BroadcastReceiver
```kotlin
class WaterReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Быстрая реакция: показать notification, запланировать work
        // быстро:
        // 1. прочитали action
        // 2. показали notification или
        // 3. запланировали WorkManager
    }
}
```

### ContentProvider

```kotlin
class HabitProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        // Инициализация provider
        return true
    }

    override fun query(...): Cursor? {
        // Чтение данных
    }

    override fun insert(...): Uri? {
        // Добавление данных
    }

    override fun update(...): Int {
        // Обновление данных
    }

    override fun delete(...): Int {
        // Удаление данных
    }

    override fun getType(uri: Uri): String? {
        // MIME/type info
    }
}
```

### Service

```kotlin
class SyncService : Service() {

    override fun onCreate() {
        super.onCreate()
        // Service создан
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Service получил команду на старт
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        // Service уничтожается
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
```

## 3. All lifecycles (activity, fragment, view), callback diffs
### Activity:
   * onCreate: создание UI, setContentView, ViewBinding, настройка toolbar, observers
   * onStart: то, что нужно пока экран видим
   * onResume: то, что нужно только когда экран активен/в фокусе: камера, sensors, animations
   * onPause: быстро остановить активные вещи, сохранить легкое состояние
   * onStop: отписаться/остановить то, что не нужно, когда экран не виден
   * onDestroy: очистка ресурсов, но нельзя полагаться, что он всегда вызовется при смерти процесса

Порядок
*    Первый запуск: onCreate -> onStart -> onResume
*    Потеря фокуса: onPause
*    Полностью скрылась: onStop
*    Вернулась после stop: onRestart -> onStart -> onResume
*    Закрылась окончательно: onPause -> onStop -> onDestroy

```kotlin
   class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Создать UI, подписки, ViewModel binding
    }

    override fun onStart() {
        super.onStart()
        // Activity стала видимой
    }

    override fun onResume() {
        super.onResume()
        // Activity в фокусе, пользователь может взаимодействовать
    }

    override fun onPause() {
        super.onPause()
        // Потеря фокуса, частично/скоро неактивна
    }

    override fun onStop() {
        super.onStop()
        // Activity больше не видима
    }

    override fun onDestroy() {
        super.onDestroy()
        // Activity уничтожается
    }
}
```

### Fragment:

* lifecycle самого Fragment
* lifecycle его View

Создание:

* onAttach() - Fragment прикрепился к Activity/Context.
* onCreate() - Fragment создан как объект. Здесь можно инициализировать не-UI логику.
* onCreateView() - Создается View из XML/layout.
* onViewCreated() - View уже создана, можно обращаться к binding.button, recyclerView и т.д.
* onStart() - Fragment видим.
* onResume() - Fragment активен.

Уничтожение:

* onPause() - Fragment теряет активность.
* onStop() - Fragment не видим.
* onDestroyView() - View фрагмента уничтожена. Нужно очистить binding.
* onDestroy() - Fragment уничтожается как объект.
* onDetach() - Fragment отсоединен от Activity.

```kotlin
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = requireArguments().getString("userId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameText.text = "Anastasia"

        binding.logoutButton.setOnClickListener {
            // logout
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
```

View может умереть раньше самого Fragment. Если держать ссылку на binding после onDestroyView(), ты
держишь старую View в памяти. Это memory leak.

```kotlin
class ProgressCircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // View прикрепилась к окну
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // View определяет свой размер
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // Размер изменился
    }

    override fun onDraw(canvas: Canvas) {
        // Рисуем на canvas
        // onDraw() не рисует XML-разметку как файл. XML уже превратился в дерево View-объектов. 
        // onDraw(canvas) — это callback, где конкретная View рисует себя на Canvas: фон, текст, линии, круги, прогресс, кастомную графику.
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // View отсоединилась от окна
    }
}
```

### Основные callbacks View:

* onAttachedToWindow(). View попала в window hierarchy.

* onMeasure(). View измеряют: какой размер она хочет/может занять.

* onLayout(). View размещает себя/детей.

* onSizeChanged(). Размер изменился.

* onDraw(). View рисует себя.

* onDetachedFromWindow(). View удалили из window hierarchy.
  Activity lifecycle:
  жизнь экрана/контейнера

Fragment lifecycle:
жизнь UI-части внутри Activity + отдельная жизнь ее View

View lifecycle:
измерение, layout, рисование, attach/detach конкретного UI-элемента

Activity.onCreate:
глобальная настройка экрана, setContentView, nav setup

Fragment.onCreate:
данные/аргументы/не-UI инициализация

Fragment.onCreateView:
inflate layout, если не используешь constructor layout id

Fragment.onViewCreated:
настройка UI, listeners, adapter, binding, observers view lifecycle

Fragment.onDestroyView:
очистить binding, adapter references к view, listeners

View.onMeasure:
рассчитать размер custom view

View.onDraw:
нарисовать custom view

View.onDetachedFromWindow:
остановить анимации/listeners view-level

### TODO: что насчет .super()
## 4. ViewBinding
* ViewBinding = сгенерированный класс для доступа к View из XML
* binding.root = корневая View layout-файла
* binding.someButton = View с id="@+id/someButton"

ViewBinding — это такой “типизированный мост” между XML и Kotlin.

```
activity_main.xml -> ActivityMainBinding
fragment_habit_details.xml -> FragmentHabitDetailsBinding
item_habit.xml -> ItemHabitBinding
```

В build.gradle.kts модуля:

```kotlin
android {
    buildFeatures {
        viewBinding = true
    }
}
```

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleText.text = "Hello Android"

        binding.saveButton.setOnClickListener {
            binding.titleText.text = "Saved"
        }
    }
}
```
```
ActivityMainBinding.inflate(layoutInflater)
создает View-дерево из XML и binding-объект

binding.root
корневая View всего layout

setContentView(binding.root)
говорит Activity: вот твой UI
```
**Важно: в Activity binding обычно можно хранить как lateinit var, потому что lifecycle Activity и ее content view обычно живут вместе.**

```kotlin
class HabitFragment : Fragment(R.layout.fragment_habit) {

    private var _binding: FragmentHabitBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHabitBinding.bind(view)

        binding.saveButton.setOnClickListener {
            binding.titleText.text = "Saved"
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
```
**Обращаться к binding можно только между onViewCreated() и onDestroyView().**

```kotlin
class HabitFragment : Fragment() {

    private var _binding: FragmentHabitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            // ...
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
```
```
Fragment(R.layout.fragment_habit)
layout создаст Fragment framework, binding делаем через bind(view)

Fragment()
layout создаем сами, binding делаем через inflate(...)
```
inflate(...):
создает View из XML.

```kotlin
val binding = ActivityMainBinding.inflate(layoutInflater)
```
bind(view):
берет уже созданную View и оборачивает ее в binding.

```kotlin
val binding = FragmentHabitBinding.bind(view)
```
То есть:
```
inflate = создай View
bind = View уже есть, дай мне binding к ней
```
```
Activity:
binding = ActivityMainBinding.inflate(layoutInflater)
setContentView(binding.root)

Fragment with Fragment(R.layout...):
_binding = FragmentHabitBinding.bind(view)

Fragment with manual inflate:
_binding = FragmentHabitBinding.inflate(inflater, container, false)
return binding.root

RecyclerView item:
ItemHabitBinding.inflate(LayoutInflater.from(parent.context), parent, false)

Fragment cleanup:
_binding = null в onDestroyView()
```

## 5. Fragment manger

FragmentManager — это объект, который управляет Fragment-ами внутри Activity или другого Fragment.

Он умеет:

* добавить Fragment;
* заменить Fragment;
* удалить Fragment;
* показать/скрыть Fragment;
* вести back stack;
* восстанавливать Fragment после configuration change;
* находить Fragment по id/tag.

```
Flutter Navigator управляет stack экранов
Android FragmentManager управляет Fragment-ами и back stack внутри Activity
```

Обычно в layout Activity есть контейнер:

```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/fragmentContainer"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

```
beginTransaction() = начинаем набор операций
replace(...) = заменить содержимое контейнера Fragment-ом
commit() = применить транзакцию
```

FragmentTransaction — это как список команд: add, replace, remove, hide, show.

Чтобы Back вернул предыдущий Fragment, добавляешь транзакцию в back stack:

```kotlin
supportFragmentManager.beginTransaction()
.replace(R.id.fragmentContainer, DetailsFragment())
.addToBackStack(null)
.commit()
```

Без addToBackStack() предыдущий Fragment не вернется по Back.

```
parentFragmentManager = управлять соседними/следующими Fragment в родительском контейнере
childFragmentManager = управлять Fragment-ами внутри текущего Fragment
```
supportFragmentManager доступен в Activity, но внутри Fragment обычно используем parentFragmentManager.


### Передача аргументов
```kotlin
class DetailsFragment : Fragment(R.layout.fragment_details) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val habitId = requireArguments().getString(ARG_HABIT_ID)
    }

    companion object {
        private const val ARG_HABIT_ID = "habitId"

        fun newInstance(habitId: String): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_HABIT_ID, habitId)
                }
            }
        }
    }
}
```
**Bundle** — это контейнер для пар key -> value, который Android умеет передавать между компонентами и сохранять при пересоздании.
Обычно в Bundle кладут:

* String
* Int
* Long
* Boolean
* Float
* Double
* массивы
* Parcelable
* иногда Serializable, но в Android чаще предпочитают Parcelable

Android при восстановлении Fragment должен уметь пересоздать его сам. Для этого ему нужен обычный пустой constructor. А arguments Android умеет сохранить и восстановить вместе с Fragment.

#### commit vs commitNow
Обычно:

`commit()`
Он планирует выполнение транзакции на main thread.

Есть еще:

`commitNow()`
Он выполняет сразу, синхронно. Его используют реже и осторожнее. Для обычной навигации почти всегда commit().

```
добавили Fragment -> onAttach/onCreate/onCreateView...
убрали Fragment -> onPause/onStop/onDestroyView...
вернули из back stack -> View может быть создана заново
```
## 6. Layouts (Frame, Linear, Constraint) diff and usage
`measure -> layout -> draw`
#### FrameLayout 
Самый простой контейнер. Он кладет детей друг на друга, как слои.(Flutter - Stack)

#### FragmentContainerView
```
FragmentContainerView специально сделан для Fragment-ов. 
Он корректнее работает с Fragment transitions, lifecycle и ограничивает использование так, чтобы внутри жили именно Fragment view. 
Исторически контейнером часто был FrameLayout, но современный выбор для Fragment — FragmentContainerView.
```
#### LinearLayout

LinearLayout раскладывает children в одну линию:

вертикально; горизонтально.
```xml
<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="vertical">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

</LinearLayout>
```
layout_width="0dp" + layout_weight="1" означает: ширину рассчитать через оставшееся место.

0dp в ConstraintLayout означает не “ноль”, а match constraints: занять доступное пространство между constraints.

#### ConstraintLayout 
Позволяет описывать позицию View через связи с другими View или parent.
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/avatarImage"
        android:layout_width="64dp"
        android:layout_height="64dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/nameText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toEndOf="@id/avatarImage"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@id/avatarImage" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
``` 
avatar.start привязан к parent.start
avatar.top привязан к parent.top

name.start привязан к avatar.end
name.end привязан к parent.end
name.top привязан к avatar.top
```

Когда использовать:
* сложные экраны;
* layout без глубокой вложенности;
* карточки/элементы списка со многими связями;
* адаптивные UI.

База Android layout:

```
wrap_content
размер по содержимому

match_parent
занять весь размер родителя

0dp в LinearLayout с weight
размер вычисляется весом

0dp в ConstraintLayout
match constraints: занять пространство между constraint-ами
```

```
margin = внешний отступ View от других View/родителя
padding = внутренний отступ содержимого внутри View
```
```
android:padding="16dp"
android:layout_margin="16dp"
```
- FrameLayout: слои, контейнер, overlay
- LinearLayout: простая строка/колонка 
- ConstraintLayout: сложные экраны без вложенности

## 7. Views (Edit text, Text input layout, ImageView, ScrollView, NestedScrollView, SwipeRefreshLayout, BottomNavigationView)
`View` — базовый строительный блок UI. Все кнопки, тексты, картинки, списки, кастомные элементы — это View или ViewGroup

Android View — mutable объект, который реально живет на экране, хранит состояние, измеряется, layout-ится и рисуется.

#### EditText — поле ввода текста.
```xml
<EditText
    android:id="@+id/nameEditText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Name"
    android:inputType="textPersonName" />
```
```kotlin
val name = binding.nameEditText.text.toString()

binding.nameEditText.setText("Anastasia")

// Слушатель изменений:
binding.nameEditText.addTextChangedListener { text ->
    // текст лежит внутри EditText как Editable, поэтому часто нужен: toString()
    val value = text.toString()
}
```
#### TextInputLayout
TextInputLayout — Material-обертка вокруг TextInputEditText. Она дает:

* floating label;
* error text;
* helper text;
* outline/filled style;
* start/end icons;
* password toggle.

```kotlin
// set Error
binding.nameInputLayout.error = "Name is required"
// remove Error
binding.nameInputLayout.error = null
```
#### ImageView
ImageView показывает изображение.
```xml
<ImageView
    android:id="@+id/avatarImage"
    android:layout_width="64dp"
    android:layout_height="64dp"
    android:src="@drawable/ic_avatar"
    android:scaleType="centerCrop" />
```
`binding.avatarImage.setImageResource(R.drawable.ic_avatar)`
**scaleType**
```
centerCrop
заполнить область, обрезая лишнее

fitCenter
поместить целиком, возможны пустые поля

centerInside
показать внутри без увеличения сверх реального размера

fitXY
растянуть по X/Y, может исказить пропорции
```
Coil - binding.avatarImage.load(url)

#### ScrollView
ScrollView — вертикальный скролл для одного child.

```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        ...
    </LinearLayout>

</ScrollView>
```
ScrollView может иметь только одного прямого child.

Если нужно много элементов, прямым child обычно делают LinearLayout/ConstraintLayout.

#### NestedScrollView
NestedScrollView — scroll container, который умеет работать во вложенных nested scrolling сценариях.

Например:

* CoordinatorLayout;
* collapsing toolbar;
* AppBarLayout;
* сложные Material screens;
* Fragment с прокручиваемым содержимым внутри parent scroll behavior.

```xml
<androidx.core.widget.NestedScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" />

</androidx.core.widget.NestedScrollView>
```

#### SwipeRefreshLayout
SwipeRefreshLayout добавляет pull-to-refresh.

Обычно оборачивает прокручиваемый child:

```xml
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    android:id="@+id/swipeRefresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/habitRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```
#### BottomNavigationView
BottomNavigationView — Material bottom tabs/navigation.

XML:

```xml
<com.google.android.material.bottomnavigation.BottomNavigationView
    android:id="@+id/bottomNavigation"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:menu="@menu/bottom_navigation_menu" />
```
Menu:

```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/habitsTab"
        android:icon="@drawable/ic_habits"
        android:title="Habits" />

    <item
        android:id="@+id/statsTab"
        android:icon="@drawable/ic_stats"
        android:title="Stats" />
</menu>
```
```kotlin
binding.bottomNavigation.setOnItemSelectedListener { item ->
    when (item.itemId) {
        R.id.habitsTab -> {
            openHabits()
            true
        }
        R.id.statsTab -> {
            openStats()
            true
        }
        else -> false
    }
}
```

```
EditText:
ввод текста

TextInputLayout:
Material-обертка поля, label/error/helper/icons

ImageView:
изображение, scaleType важен

ScrollView:
скролл одного child, не для больших списков

NestedScrollView:
скролл для nested/coordinator scenarios

SwipeRefreshLayout:
pull-to-refresh

BottomNavigationView:
нижняя навигация по разделам
```

## 8. RecyclerView + Adapter + ViewHolder.
- RecyclerView
сам контейнер списка

- LayoutManager
решает, как расположить элементы: vertical list, horizontal list, grid

- Adapter
соединяет данные со списком

- ViewHolder
держит View одного item-а

- Item layout
XML одного элемента списка

in Flutter 
```
RecyclerView ≈ ListView.builder
Adapter ≈ itemBuilder + источник данных
ViewHolder ≈ переиспользуемая оболочка item view
LayoutManager ≈ scrollDirection/grid delegate/layout behavior
```
```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="16dp">

    <TextView
        android:id="@+id/titleText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/descriptionText"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintStart_toStartOf="@id/titleText"
        app:layout_constraintEnd_toEndOf="@id/titleText"
        app:layout_constraintTop_toBottomOf="@id/titleText" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
#### ViewHolder
ViewHolder хранит binding одного item-а и умеет привязать данные. Потому что item view переиспользуются. Когда ты скроллишь, RecyclerView не создает бесконечно новые View, а берет старую item view и вызывает bind() с новыми данными.
Должен быть простым(тупеньким)
Он не должен:
* ходить в базу;
* открывать Fragment напрямую;
* знать про FragmentManager;
* запускать сетевые запросы;
* менять глобальное состояние.

```kotlin
class HabitViewHolder(
    private val binding: ItemHabitBinding,
    private val onClick: (Habit) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Habit) {
        binding.titleText.text = item.title
        binding.descriptionText.text = item.description

        binding.root.setOnClickListener {
            onClick(item)
        }
    }
}
```
#### Adapter
Adapter отвечает за:
* сколько item-ов;
* создать ViewHolder;
* привязать данные к ViewHolder.

```kotlin
class HabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {

    private val items = mutableListOf<Habit>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HabitViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitItems(newItems: List<Habit>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
```
Использования
```kotlin
class HabitListFragment : Fragment(R.layout.fragment_habit_list) {

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val adapter = HabitAdapter { habit ->
        openDetails(habit.id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHabitListBinding.bind(view)

        binding.habitRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.habitRecyclerView.adapter = adapter

        adapter.submitItems(
            listOf(
                Habit("1", "Drink water", "2 liters per day", false),
                Habit("2", "Read", "20 pages", false),
                Habit("3", "Walk", "30 minutes", true)
            )
        )
    }

    override fun onDestroyView() {
        binding.habitRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private fun openDetails(habitId: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HabitDetailsFragment.newInstance(habitId))
            .addToBackStack(null)
            .commit()
    }
}
```

LayoutManager

Вертикальный список:

`binding.habitRecyclerView.layoutManager = LinearLayoutManager(requireContext())`

Горизонтальный:

`binding.habitRecyclerView.layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)`

Grid:

`binding.habitRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)`

#### notifyDataSetChanged vs DiffUtil
Базовый adapter:

`notifyDataSetChanged()`
Минусы:
* перерисовывает все;
* нет красивых анимаций изменений;
* хуже производительность;
* сложнее понимать точечные изменения.

Современнее:

`ListAdapter<Habit, HabitViewHolder>(HabitDiffCallback)`
Он сравнивает старый и новый список и обновляет только изменившиеся item-ы.

Но для первой ручной тренировки можно начать с обычного RecyclerView.Adapter, а потом я попрошу тебя переписать на ListAdapter + DiffUtil.
## 9. Material views
10. Shared prefs & DataStore
11. Canvas, custom view, surface view
12. intent types
13. types of services (background or foreground)
14. workers (work manager)
15. delegates

## Libs

1. Retrofit, OkHttp3, Logging Interceptor, Chucker interceptor, kotlinx serialization + kotlinx serialization json (+ converter factory)
2. koin/dagger2/metro (DI)
3. room/realm (DB)
4. NavComponent, Cicerone
5. Glide or Picasso