/*FooTable Init*/
$(function () {
	"use strict";

	/*Init FooTable*/
	$('#footable_1,#footable_3').footable();

	/*Editing FooTable*/

	var $modal = $('#editor-modal'),
		$editor = $('#editor'),
		$editorTitle = $('#editor-title'),
		ft = FooTable.init('#footable_2', {
			editing: {
				enabled: true,
				addRow: function(){
					$modal.removeData('row');
					$editor[0].reset();
					$editorTitle.text('Add a new row');
					$modal.modal('show');
				},
				editRow: function(row){
					var values = row.val();
					$editor.find('#id').val(values.id);
					$editor.find('#NumberOfPositionsRequired').val(values.NumberOfPositionsRequired);
					$("#WasteType").selectpicker("refresh");
					$editor.find('#QualificationRequirements').val(values.QualificationRequirements);
					$editor.find('#DutyStation').val(values.DutyStation);
					$editor.find('#DepartmentDivision').val(values.DepartmentDivision);
					$editor.find('#BriefDescription').val(values.BriefDescription);



					$modal.data('row', row);
					$editorTitle.text('Edit row #' + values.id);
					$modal.modal('show');
				},
				deleteRow: function(row){
					if (confirm('Are you sure you want to delete the row?')){
						row.delete();
					}
				}
			}
		}),
		uid = 10;


	$editor.on('submit', function(e){
		if (this.checkValidity && !this.checkValidity()) return;
		e.preventDefault();

		var row = $modal.data('row'),

			values = {

				id: $editor.find('#id').val(),
				NumberOfPositionsRequired: $editor.find('#NumberOfPositionsRequired').val(),

				QualificationRequirements: $editor.find('#QualificationRequirements').val(),
				DutyStation: $editor.find('#DutyStation').val(),
				DepartmentDivision: $editor.find('#DepartmentDivision').val(),
				BriefDescription: $editor.find('#BriefDescription').val()
			};

		if (row instanceof FooTable.Row){
			row.val(values);
		} else {

			values.id = uid++;
			ft.rows.add(values);

		}
		$modal.modal('hide');
	});
});
		