package br.com.dhsoftware.workerday.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import br.com.dhsoftware.workerday.FragmentController;
import br.com.dhsoftware.workerday.R;
import br.com.dhsoftware.workerday.dao.Dao;
import br.com.dhsoftware.workerday.model.Registry;
import br.com.dhsoftware.workerday.util.DateUtil;
import br.com.dhsoftware.workerday.util.DialogUtil;
import br.com.dhsoftware.workerday.util.FileUtil;
import br.com.dhsoftware.workerday.util.ImageUtil;
import br.com.dhsoftware.workerday.util.IntentUtil;
import br.com.dhsoftware.workerday.util.PermissionUtil;
import br.com.dhsoftware.workerday.util.JSONUser;
import br.com.dhsoftware.workerday.util.TouchEditText;
import br.com.dhsoftware.workerday.model.enumObservation;


public class AddRegistryFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener, TimePickerDialog.OnTimeSetListener, RadioGroup.OnCheckedChangeListener
        , Serializable {
    private View view;
    private EditText editTextDateWorked, editTextEntranceTime, editTextEntranceLunchTime, editTextLeaveLunchTime,
            editTextLeaveTime, editTextRequiredTime, editTextPercentExtraWork, editTextTimeDeclaration;
    private EditText editTextSetDataOrTimeFromPicker;
    private RadioButton radioButtonAtestado, radioButtonDeclaration, radioButtonNothing, radioButtonAbsence;
    private Registry registryBundle;
    private FragmentController fragmentController;
    private ImageView imageViewEntrance, imageViewLeave, imageViewEntranceLunch, imageViewLeaveLunch;
    private File photoFile;
    private Dao dao;
    private ImageUtil imageUtil;
    private TouchEditText touch;
    private FileUtil fileUtil;
    private PermissionUtil permissionUtil;
    private IntentUtil intentUtil;

    private DialogUtil dialogUtil;

    private String imagePathActually, imagePathEntrance, imagePathLeave, imagePathEntranceLunch, imagePathLeaveLunch;

    public AddRegistryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_add_registry, container, false);
        touch = new TouchEditText();
        imageUtil = new ImageUtil(getContext());
        setView();

        registryBundle = null;
        Bundle bundle = null;
        bundle = getArguments();
        try {
            registryBundle = (Registry) Objects.requireNonNull(bundle).getSerializable("registry");
        } catch (Exception e) {
            e.getMessage();
        }


        if (registryBundle != null) {
            setRegistryFromBundle();
        }

        return view;
    }

    private void setRegistryFromBundle() {
        editTextDateWorked.setText(registryBundle.getDayString());
        editTextEntranceTime.setText(registryBundle.getEntranceString());
        editTextEntranceLunchTime.setText(registryBundle.getEntranceLunchString());
        editTextLeaveLunchTime.setText(registryBundle.getLeaveLunchString());
        editTextLeaveTime.setText(registryBundle.getLeaveString());
        editTextRequiredTime.setText(registryBundle.getRequiredTimeToWorkString());
        editTextPercentExtraWork.setText(String.valueOf(registryBundle.getPercent()));


        if (!isObjectNullOrEmptyFromRegistry(registryBundle.getImageEntrance())) {
            imageUtil.putImageInImageView(imageViewEntrance, registryBundle.getImageEntrance());
        }


        if (!isObjectNullOrEmptyFromRegistry(registryBundle.getImageLeave())) {
            imageUtil.putImageInImageView(imageViewLeave, registryBundle.getImageLeave());
        }


        if (!isObjectNullOrEmptyFromRegistry(registryBundle.getImageEntranceLunch())) {
            imageUtil.putImageInImageView(imageViewEntranceLunch, registryBundle.getImageEntranceLunch());
        }


        if (!isObjectNullOrEmptyFromRegistry(registryBundle.getImageLeaveLunch())) {
            imageUtil.putImageInImageView(imageViewLeaveLunch, registryBundle.getImageLeaveLunch());
        }

        if (registryBundle.getObservationString().equals(enumObservation.NORMAL.toString())) {
            radioButtonNothing.setChecked(true);
        }
        if (registryBundle.getObservationString().equals(enumObservation.ATESTADO.toString())) {
            radioButtonAtestado.setChecked(true);
        }
        if (registryBundle.getObservationString().equals(enumObservation.DECLARACAO_DE_HORAS.toString())) {
            radioButtonDeclaration.setChecked(true);
            editTextTimeDeclaration.setText(registryBundle.getTimeDeclarationString());
        }
        if (registryBundle.getObservationString().equals(enumObservation.ABSENCE.toString())) {
            radioButtonAbsence.setChecked(true);
        }

    }

    private boolean isObjectNullOrEmptyFromRegistry(String string) {
        if (string != null) {
            if (!string.equals(""))
                return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        dao = new Dao(getActivity());

        fragmentController = new FragmentController(getFragmentManager());


        dialogUtil = new DialogUtil(getActivity());

        fileUtil = new FileUtil(getContext());
        permissionUtil = new PermissionUtil(getContext(), getActivity());
        intentUtil = new IntentUtil(getContext());
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setView() {
        editTextDateWorked = view.findViewById(R.id.editText_dateWorked_registry);
        editTextDateWorked.setOnClickListener(this);

        editTextEntranceTime = view.findViewById(R.id.editText_entranceTime_registry);
        editTextEntranceTime.setOnClickListener(this);
        editTextEntranceTime.setOnTouchListener(touch);

        editTextEntranceLunchTime = view.findViewById(R.id.editText_entranceLunchTime_registry);
        editTextEntranceLunchTime.setOnClickListener(this);
        editTextEntranceLunchTime.setOnTouchListener(touch);

        editTextLeaveLunchTime = view.findViewById(R.id.editText_leaveLunchTime_registry);
        editTextLeaveLunchTime.setOnClickListener(this);
        editTextLeaveLunchTime.setOnTouchListener(touch);

        editTextLeaveTime = view.findViewById(R.id.editText_leaveTime_registry);
        editTextLeaveTime.setOnClickListener(this);
        editTextLeaveTime.setOnTouchListener(touch);

        editTextRequiredTime = view.findViewById(R.id.editText_requiredTimeToWorkTime_registry);
        editTextRequiredTime.setOnClickListener(this);
        editTextRequiredTime.setOnTouchListener(touch);

        editTextPercentExtraWork = view.findViewById(R.id.editText_porcentExtraWork_registry);
        editTextPercentExtraWork.setOnClickListener(this);

        RadioGroup buttonGroup = view.findViewById(R.id.button_group_registry);
        buttonGroup.setOnCheckedChangeListener(this);

        radioButtonAtestado = view.findViewById(R.id.button_atestado_registry);
        radioButtonDeclaration = view.findViewById(R.id.button_timeDeclaration_registry);
        radioButtonNothing = view.findViewById(R.id.button_nothing_registry);
        radioButtonAbsence = view.findViewById(R.id.button_absence_registry);


        CardView cardViewEntrance = view.findViewById(R.id.cardView_entrance_AddRegistry);
        cardViewEntrance.setOnClickListener(this);

        CardView cardViewEntranceLunch = view.findViewById(R.id.cardView_entranceLunch_AddRegistry);
        cardViewEntranceLunch.setOnClickListener(this);

        CardView cardViewLeave = view.findViewById(R.id.cardView_leave_AddRegistry);
        cardViewLeave.setOnClickListener(this);

        CardView cardViewLeaveLunch = view.findViewById(R.id.cardView_leaveLunch_AddRegistry);
        cardViewLeaveLunch.setOnClickListener(this);

        imageViewEntrance = view.findViewById(R.id.imageView_entrance_addRegistry);
        imageViewEntrance.setTag("");

        imageViewLeave = view.findViewById(R.id.imageView_leave_addRegistry);
        imageViewLeave.setTag("");

        imageViewEntranceLunch = view.findViewById(R.id.imageView_entranceLunch_addRegistry);
        imageViewEntranceLunch.setTag("");

        imageViewLeaveLunch = view.findViewById(R.id.imageView_leaveLunch_addRegistry);
        imageViewLeaveLunch.setTag("");

        editTextTimeDeclaration = view.findViewById(R.id.editText_declaration_addRegistry);
        editTextTimeDeclaration.setOnClickListener(this);

        ImageButton saveButton = view.findViewById(R.id.button_save_registry);
        saveButton.setOnClickListener(this);

        completeAutomaticPercentExtraSalaryAndRequiredTimeField();
    }

    private void completeAutomaticPercentExtraSalaryAndRequiredTimeField() {
        JSONUser jsonUser = new JSONUser(getActivity());
        if (!jsonUser.getPercentExtraSalary().equals(""))
            editTextPercentExtraWork.setText(jsonUser.getPercentExtraSalary());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        /*month++ serve para arrumar o bug que retorna sempre o numero do mês anterior
        Também verifico se o mês ou o dia é menor que 10 para adicionar um 0 na frente, pois é necessario para o
        SimpleFormatData
         */
        month++;
        String date = DateUtil.getInstanceDateUtil().formatCorrectStringDate(dayOfMonth, month, year);


        //Irá verificar se a Data já existe no banco de dados, se existir não irá adicionar
        if (checkDateFromDao(date)) {
            editTextSetDataOrTimeFromPicker.setText(date);
        }


    }

    private boolean checkDateFromDao(String date) {
        if (dao.isDateSet(date)) {
            dialogUtil.infoDialog(getString(R.string.date_already_registred));
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        /*
        Verifica se  a hora e/ou minuto é menor que 10 para colocar o 0 da frente
        Para ficar no formato do SimpleDataFormat
         */
        String time = DateUtil.getInstanceDateUtil().formatCorrectStringTime(hourOfDay, minute);

        editTextSetDataOrTimeFromPicker.setText(validatorEditTextTime(time));
    }

    private String checkEntranceEditText(String time) {
        Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_entrance_leave);
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_entrance_entranceLunch);
            }
        } else if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_entrance_leaveLunch);
            }
        }

        return "";

    }

    private String checkLeaveEditText(String time) {
        Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_leave_entrance);
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() > calendarLeave.getTime().getTime()) {
                return getString(R.string.validator_leave_entranceLunch);
            }
        }
        if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() > calendarLeave.getTime().getTime()) {
                return getString(R.string.validator_leave_leaveLunch);
            }
        }

        return "";
    }

    private String checkEntranceLunchEditText(String time) {
        Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return getString(R.string.validator_entranceLunch_leave);
            }
        }
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarEntranceLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_entranceLunch_entrance);
            }
        }
        if (!editTextLeaveLunchTime.getText().toString().equals("")) {
            Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return getString(R.string.validator_entranceLunch_leaveLunch);
            }
        }

        return "";
    }

    private String checkLeaveLunchEditText(String time) {
        Calendar calendarLeaveLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(time);
        if (!editTextLeaveTime.getText().toString().equals("")) {
            Calendar calendarLeave = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString());
            if (calendarLeave.getTime().getTime() < calendarLeaveLunch.getTime().getTime()) {
                return getString(R.string.validator_leaveLunch_leave);
            }
        }
        if (!editTextEntranceTime.getText().toString().equals("")) {
            Calendar calendarEntrance = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntrance.getTime().getTime()) {
                return getString(R.string.validator_leaveLunch_entrance);
            }
        }
        if (!editTextEntranceLunchTime.getText().toString().equals("")) {
            Calendar calendarEntranceLunch = DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString());
            if (calendarLeaveLunch.getTime().getTime() < calendarEntranceLunch.getTime().getTime()) {
                return getString(R.string.validator_leaveLunch_entranceLunch);
            }
        }

        return "";
    }

    private String validatorEditTextTime(String time) {
        if (editTextEntranceTime == editTextSetDataOrTimeFromPicker) {
            if (!checkEntranceEditText(time).equals("")) {
                dialogUtil.infoDialog(checkEntranceEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextEntranceLunchTime == editTextSetDataOrTimeFromPicker) {
            if (!checkEntranceLunchEditText(time).equals("")) {
                dialogUtil.infoDialog(checkEntranceLunchEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextLeaveLunchTime == editTextSetDataOrTimeFromPicker) {
            if (!checkLeaveLunchEditText(time).equals("")) {
                dialogUtil.infoDialog(checkLeaveLunchEditText(time));
                return "";
            } else {
                return time;
            }
        } else if (editTextLeaveTime == editTextSetDataOrTimeFromPicker) {
            if (!checkLeaveEditText(time).equals("")) {
                dialogUtil.infoDialog(checkLeaveEditText(time));
                return "";
            } else {
                return time;
            }
        }

        return time;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAtestado.getId()))) {
            enableEditText(false);
            editTextTimeDeclaration.setVisibility(View.INVISIBLE);
            editTextRequiredTime.setText("");
            setEditTextEmpty();
        } else
            enableEditText(true);

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonDeclaration.getId()))) {
            editTextTimeDeclaration.setVisibility(View.VISIBLE);
        }

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonAbsence.getId()))) {
            enableEditText(false);
            editTextTimeDeclaration.setVisibility(View.INVISIBLE);
            setEditTextEmpty();
        }

        if (String.valueOf(group.getCheckedRadioButtonId()).equals(String.valueOf(radioButtonNothing.getId()))) {
            editTextTimeDeclaration.setVisibility(View.INVISIBLE);

        }
    }

    private void setEditTextEmpty() {
        editTextEntranceLunchTime.setText("");
        editTextEntranceTime.setText("");
        editTextLeaveLunchTime.setText("");
        editTextLeaveTime.setText("");
        editTextPercentExtraWork.setText("");
        editTextTimeDeclaration.setText("");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.editText_declaration_addRegistry:
                setEditTextSetDataOrTimeFromPicker(editTextTimeDeclaration);
                timePickerDialog();
                break;

            case R.id.editText_dateWorked_registry:
                setEditTextSetDataOrTimeFromPicker(editTextDateWorked);
                datePickerDialog();
                break;

            case R.id.editText_entranceTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextEntranceTime);
                timePickerDialog();
                break;

            case R.id.editText_entranceLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextEntranceLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveLunchTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextLeaveLunchTime);
                timePickerDialog();
                break;
            case R.id.editText_leaveTime_registry:
                setEditTextSetDataOrTimeFromPicker(editTextLeaveTime);
                timePickerDialog();
                break;
            case R.id.editText_requiredTimeToWorkTime_registry:
                timePickerDialog();
                setEditTextSetDataOrTimeFromPicker(editTextRequiredTime);
                break;
            case R.id.button_save_registry:
                validator();
                break;
            case R.id.cardView_entrance_AddRegistry:
                imagePathEntrance = fileUtil.getNewPath();
                imagePathActually = imagePathEntrance;
                checkImageView(imageViewEntrance);
                break;
            case R.id.cardView_leave_AddRegistry:
                imagePathLeave = fileUtil.getNewPath();
                imagePathActually = imagePathLeave;
                checkImageView(imageViewLeave);
                break;
            case R.id.cardView_leaveLunch_AddRegistry:
                imagePathLeaveLunch = fileUtil.getNewPath();
                imagePathActually = imagePathLeaveLunch;
                checkImageView(imageViewLeaveLunch);
                break;
            case R.id.cardView_entranceLunch_AddRegistry:
                imagePathEntranceLunch = fileUtil.getNewPath();
                imagePathActually = imagePathEntranceLunch;
                checkImageView(imageViewEntranceLunch);
                break;
        }
    }


    private void startIntent() {
        if (permissionUtil.isTherePermission()) {
            photoFile = new File(imagePathActually);

            startActivityForResult(intentUtil.startIntentGetImage(photoFile), 1);
        } else {
            permissionUtil.requestPermission();
        }
    }

    private void checkImageView(ImageView imageView){
        if (!imageView.getTag().toString().equals(""))
            dialogUtil.imageDialog(imageView);
        else
            startIntent();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                dialogUtil.showToast(getString(R.string.image_received), Toast.LENGTH_LONG);

                if (data == null) {
                    imageUtil.compressImage(imagePathActually);
                    imageUtil.putImageInImageView(checkImageViewSelected(), imagePathActually);
                } else {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    File filePath = new File(picturePath);
                    try {
                        fileUtil.copyFile(filePath, photoFile);
                    } catch (IOException io) {
                        io.printStackTrace();
                    }

                    imageUtil.compressImage(imagePathActually);
                    imageUtil.putImageInImageView(checkImageViewSelected(), imagePathActually);
                }
            }
        }
    }

    private ImageView checkImageViewSelected() {
        if (imagePathActually.equals(imagePathEntrance))
            return imageViewEntrance;
        if (imagePathActually.equals(imagePathLeave))
            return imageViewLeave;
        if (imagePathActually.equals(imagePathEntranceLunch))
            return imageViewEntranceLunch;
        if (imagePathActually.equals(imagePathLeaveLunch))
            return imageViewLeaveLunch;

        return null;
    }


    private void timePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void datePickerDialog() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    private void enableEditText(boolean active) {
        editTextEntranceTime.setEnabled(active);
        editTextEntranceLunchTime.setEnabled(active);
        editTextLeaveTime.setEnabled(active);
        editTextLeaveLunchTime.setEnabled(active);
        editTextRequiredTime.setEnabled(active);
        editTextPercentExtraWork.setEnabled(active);
    }


    private void validator() {
        if (editTextDateWorked.getText().toString().equals("")) {
            editTextDateWorked.setHintTextColor(Color.RED);
            dialogUtil.infoDialog(getString(R.string.error_validator));
        } else if (radioButtonDeclaration.isChecked()) {
            if (editTextTimeDeclaration.getText().toString().equals("")) {
                editTextTimeDeclaration.setHintTextColor(Color.RED);
            }
        } else {
            dialogUtil.showToast(getString(R.string.registry_save), Toast.LENGTH_SHORT);
            createObjectRegistry();
            fragmentController.goBackFragment();
        }
    }

    private void createObjectRegistry() {
        Registry registry = new Registry();

        if (radioButtonAbsence.isChecked())
            registry.setObservation(enumObservation.ABSENCE);

        if (radioButtonAtestado.isChecked())
            registry.setObservation(enumObservation.ATESTADO);


        if (radioButtonDeclaration.isChecked()) {
            registry.setObservation(enumObservation.DECLARACAO_DE_HORAS);
            registry.setTimeDeclaration(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextTimeDeclaration.getText().toString()));
        }

        if (radioButtonNothing.isChecked())
            registry.setObservation(enumObservation.NORMAL);


        registry.setDay(DateUtil.getInstanceDateUtil().convertStringDateToCalendar(editTextDateWorked.getText().toString()));
        registry.setEntrance(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceTime.getText().toString()));
        registry.setEntranceLunch(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextEntranceLunchTime.getText().toString()));
        registry.setLeaveLunch(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveLunchTime.getText().toString()));
        registry.setLeave(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextLeaveTime.getText().toString()));
        //if (!editTextPercentExtraWork.getText().toString().equals("") && !editTextPercentExtraWork.getText().toString().equals(null)) {

        if (!editTextPercentExtraWork.getText().toString().equals("")) {
            registry.setPercent(Integer.parseInt(editTextPercentExtraWork.getText().toString()));
        } else {
            registry.setPercent(0);
        }

        registry.setRequiredTimeToWork(DateUtil.getInstanceDateUtil().convertStringTimeToCalendar(editTextRequiredTime.getText().toString()));


        if (imageViewEntrance.getTag() != null)
            registry.setImageEntrance(imageViewEntrance.getTag().toString());
        if (imageViewLeave.getTag() != null)
            registry.setImageLeave(imageViewLeave.getTag().toString());
        if (imageViewEntranceLunch.getTag() != null)
            registry.setImageEntranceLunch(imageViewEntranceLunch.getTag().toString());
        if (imageViewLeaveLunch.getTag() != null)
            registry.setImageLeaveLunch(imageViewLeaveLunch.getTag().toString());

        insertOrSetModifyInDao(registry);

    }

    private void insertOrSetModifyInDao(Registry registry) {
        if (registryBundle != null) {
            registry.setId(registryBundle.getId());
            dao.saveModificationRegistry(registry);
        } else {
            dao.insertRegistryToDao(registry);
        }
    }


    private void setEditTextSetDataOrTimeFromPicker(EditText editTextSetDataOrTimeFromPicker) {
        this.editTextSetDataOrTimeFromPicker = editTextSetDataOrTimeFromPicker;
    }


    @Override
    public void onStop() {
        super.onStop();
        dao.close();
    }
}
