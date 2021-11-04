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
					$editor.find('#id').val(values.id);
					$editorTitle.text('Add a new row');
					$editor.find("#sub").val("Submit");
					$modal.modal('show');
				},
				editRow: function(row){
					var values = row.val();
					$editor.find('#id').val(values.id);
					$editor.find('#NumberOfPositionsRequired').val(values.NumberOfPositionsRequired);
					$("#WasteType").selectpicker("refresh");
					$editor.find("#sub").val("Edit");
					$editor.find('#QualificationRequirements').val(values.QualificationRequirements);
					$editor.find('#DutyStation').val(values.DutyStation);
					$editor.find('#DepartmentDivision').val(values.DepartmentDivision);
					$editor.find('#BriefDescription').val(values.BriefDescription);
					$editor.find('#level').val(values.level);
					$("#level").selectpicker("refresh");
					$editor.find('#field').val(values.field);
					$("#field").selectpicker("refresh");
					$editor.find('#req').val(values.id);


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
		//uid = document.getElementById("count").value;
		uid=1;

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
				level: $editor.find('#level').val(),
				field: $editor.find('#field').val(),
				BriefDescription: $editor.find('#BriefDescription').val()
			};

		if (row instanceof FooTable.Row){
			row.val(values);
		} else {
			$("#level").selectpicker("refresh");
			$("#field").selectpicker("refresh");

			values.id = uid++;
			ft.rows.add(values);

		}
		$modal.modal('hide');
	});
});
		