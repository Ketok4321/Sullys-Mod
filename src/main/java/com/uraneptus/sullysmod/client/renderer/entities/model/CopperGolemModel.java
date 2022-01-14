package com.uraneptus.sullysmod.client.renderer.entities.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.blueprint.core.Blueprint;
import com.teamabnormals.blueprint.core.endimator.Endimation;
import com.teamabnormals.blueprint.core.endimator.Endimator;
import com.uraneptus.sullysmod.SullysMod;
import com.uraneptus.sullysmod.common.entities.CopperGolemEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;



/*public class CopperGolemModel extends AnimatedGeoModel<CopperGolemEntity> {

    @Override
    public ResourceLocation getModelLocation(CopperGolemEntity entity) {
        return new ResourceLocation(SullysMod.MOD_ID, "geo/copper_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CopperGolemEntity entity) {
        int id = entity.getEntityData().get(CopperGolemEntity.OXIDIZATION);
        return new ResourceLocation(SullysMod.MOD_ID, "textures/entity/copper_golem/copper_golem_" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CopperGolemEntity entity) {
        return new ResourceLocation(SullysMod.MOD_ID, "animations/copper_golem.animation.json");
    }
}*/

// Made by Sully using Blockbench 4.0.2
public class CopperGolemModel extends EntityModel<CopperGolemEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(SullysMod.MOD_ID, "copper_golem"), "main");

	private static final Endimation WALKING = Blueprint.ENDIMATION_LOADER.getEndimation(new ResourceLocation(SullysMod.MOD_ID, "walking"));
	private final ModelPart root;
	private final Endimator endimator;

	public CopperGolemModel(ModelPart root) {
		this.root = root.getChild("root");
		this.endimator = Endimator.compile(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));
		PartDefinition core = root.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.0F));
		PartDefinition rightLeg = core.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(32, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 4.0F, 1.0F));
		PartDefinition leftLeg = core.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(32, 18).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 4.0F, 1.0F));
		PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -2.0F, -3.0F, 9.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -7.0F, -5.0F, 11.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.5F, -4.0F, -7.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));
		PartDefinition screw = head.addOrReplaceChild("screw", CubeListBuilder.create().texOffs(16, 31).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(33, 0).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 1.0F));
		PartDefinition leftArm = root.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(28, 27).mirror().addBox(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.5F, -10.0F, 0.0F));
		PartDefinition rightArm = root.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(28, 27).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -10.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}

	private static float computeWalkTime(float limbSwing, float length) {
		float period = length * 5.0F;
		return (((limbSwing + period) % period) / period) * length;
	}

	@Override
	public void setupAnim(CopperGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		assert WALKING != null;
		float length = WALKING.getLength();
		float adjustedLimbSwingAmount = 4.0F * limbSwingAmount / length;
		if (adjustedLimbSwingAmount > 1.0F) {
			adjustedLimbSwingAmount = 1.0F;
		}
		this.endimator.apply(WALKING, computeWalkTime(limbSwing, length), adjustedLimbSwingAmount, Endimator.ResetMode.ALL);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, buffer, packedLight, packedOverlay);
	}
}