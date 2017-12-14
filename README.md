TransportResolver - Android - Room Architecture
===== 

Cette projet contient la declaration de la base de donées ainsi comme les
donées stocké en suivant la _Nouvelle Architecture_ suggérée par Android Developpers
appelée _Room_.

# Room

Room fournit un couche d'abstraction sur SQLite pour permettre un accès
fluide à la base de données tout en exploitant toute la puissance de SQLite

_Reference_ : https://developer.android.com/topic/libraries/architecture/room.html

# Description detaillée de l'application

Cette application manipule la base de donées Transports.db gràce à l'application
TransportProvider.

## Base de donées

La base de donées contient un tableau qui s'appelle _transports_.

### Les attributes du tableau transports :
* number text primary key.
* capacity integer.
* color text.

La structure du tableau se trouve dans la class 
_fr.lr.iut.transportprovider.models.Transport_

### Declaration des méthodes pour accéder au fourniseur TransportProvider
* addTransport()
* showAllTransports()
* deleteAllTransports() 
```java
public class MainActivity extends AppCompatActivity {

    // widgets
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_capacity)
    EditText edtCapacity;
    @BindView(R.id.edt_color)
    EditText edtColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.edt_color)
    public void showColorPicker() {
        SpectrumDialog.Builder colorPicker = new SpectrumDialog.Builder(this);
        colorPicker.setColors(R.array.md_colors);
        colorPicker.setSelectedColorRes(R.color.md_blue_500);
        colorPicker.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
            @Override
            public void onColorSelected(boolean positiveResult, int color) {
                edtColor.setText("#" + Integer.toHexString(color));
            }
        });
        colorPicker.build().show(getSupportFragmentManager(), "Color Picker");
    }

    // Declaration des méthodes pour accéder au fourniseur TransportProvider
    @OnClick(R.id.btn_add_a_new_transport)
    public void addTransport() {
        ContentValues contentValues = new ContentValues();

        contentValues.put("number", edtNumber.getText().toString());
        contentValues.put("capacity", edtCapacity.getText().toString());
        contentValues.put("color", edtColor.getText().toString());

        Uri uri = getContentResolver().insert(PC.Transport.TRANSPORT_ITEM, contentValues);

        Snackbar.make(findViewById(android.R.id.content), "Transport App : " +
                uri.toString() + " inserted", Toast.LENGTH_SHORT).show();

        edtNumber.setText("");
        edtCapacity.setText("");
        edtColor.setText("");
    }

    @OnClick(R.id.btn_show_all_transports)
    public void showAllTransports() {
        Cursor cursor = getContentResolver().query(PC.Transport.TRANSPORT_DIR, null, null, null, "number");
        String result = "Transport App Results: ";

        TransportsActivity.TRANSPORTS = new ArrayList<>();
        if (!cursor.moveToFirst())
            Snackbar.make(findViewById(android.R.id.content), result + " no content yet!",
                    Toast.LENGTH_SHORT).show();
        else
            do {
                String number = cursor.getString(0);
                int capacity = cursor.getInt(1);
                String color = cursor.getString(2);

                Transport transport = new Transport(number, capacity, color);
                TransportsActivity.TRANSPORTS.add(transport);
            } while (cursor.moveToNext());

        Intent intent = new Intent(this, TransportsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_delete_all_transports)
    public void deleteAllTransports() {
        int count = getContentResolver().delete(PC.Transport.TRANSPORT_DIR, null, null);
        String countNum = "Transport App : " + count + " records were deleted.";
        Snackbar.make(findViewById(android.R.id.content), countNum, Toast.LENGTH_SHORT).show();
    }
}
```